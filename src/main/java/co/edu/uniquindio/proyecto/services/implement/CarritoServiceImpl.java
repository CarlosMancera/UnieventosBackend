package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.ordenDTO.AgregarAlCarritoDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.ResumenCarritoDTO;
import co.edu.uniquindio.proyecto.model.docs.Carrito;
import co.edu.uniquindio.proyecto.model.docs.Cuenta;
import co.edu.uniquindio.proyecto.model.docs.Evento;
import co.edu.uniquindio.proyecto.model.vo.DetalleCarrito;
import co.edu.uniquindio.proyecto.model.vo.Localidad;
import co.edu.uniquindio.proyecto.repositories.CarritoRepo;
import co.edu.uniquindio.proyecto.repositories.CuentaRepo;
import co.edu.uniquindio.proyecto.repositories.EventoRepo;
import co.edu.uniquindio.proyecto.services.interfaces.CarritoService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private final CarritoRepo carritoRepo;
    private final CuentaRepo cuentaRepo;
    private final EventoRepo eventoRepo;


    //TODO
    @Override
    @Transactional
    public String agregarAlCarrito(AgregarAlCarritoDTO agregarAlCarritoDTO) throws Exception {
        Cuenta cuenta = cuentaRepo.findById(agregarAlCarritoDTO.idCuenta().toString())
                .orElseThrow(() -> new Exception("Cuenta no encontrada"));

        Evento evento = eventoRepo.findById(agregarAlCarritoDTO.idEvento().toString())
                .orElseThrow(() -> new Exception("Evento no encontrado"));

        double precioUnitario = 0;

        for(Localidad l : evento.getLocalidades()){
            if(l.getNombre().equals(agregarAlCarritoDTO.localidad())){
                precioUnitario = l.getPrecio();
                break;
            }
        }

        DetalleCarrito detalle = DetalleCarrito.builder()
                .idEvento(new ObjectId(evento.getId()))
                .nombreLocalidad(agregarAlCarritoDTO.localidad())
                .cantidad(agregarAlCarritoDTO.cantidad())
                .precioUnitario(precioUnitario)
                .build();

        Carrito carrito = carritoRepo.findByIdUsuario(cuenta.getId())
                .orElse(
                        Carrito.builder()
                                .idUsuario(new ObjectId(cuenta.getId()))
                                .fecha(LocalDateTime.now())
                                .items(new ArrayList<>())
                                .build()
                );

        carrito.getItems().add(detalle);
        carritoRepo.save(carrito);

        return carrito.getId();
    }

    @Override
    @Transactional
    public void eliminarDelCarrito(ObjectId idCuenta, ObjectId idEvento) throws Exception {
        Carrito carrito = carritoRepo.findByIdUsuario(idCuenta)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        carrito.getItems().removeIf(e -> e.getIdEvento().equals(idEvento));
        carritoRepo.save(carrito);
    }

    @Override
    public List<ResumenCarritoDTO> listarCarrito(ObjectId idCuenta) throws Exception {
        return List.of();
    }

    //----------------------AUX-----------------------
    private ResumenCarritoDTO mapToResumenCarritoDTO(Entrada entrada) {
        Evento evento = eventoRepo.findById(entrada.getEvento()).orElseThrow();
        return new ResumenCarritoDTO(
                entrada.getId(),
                evento.getNombre(),
                evento.getFecha(),
                entrada.getLocalidad(),
                entrada.getCantidad(),
                entrada.getPrecioUnitario()
        );
    }

    private double calcularSubtotal(Carrito carrito) {
        return carrito.getEntradas().stream()
                .mapToDouble(e -> e.getPrecioUnitario() * e.getCantidad())
                .sum();
    }


}
