package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.ordenDTO.AgregarAlCarritoDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.ResumenCarritoDTO;
import co.edu.uniquindio.proyecto.model.docs.Carrito;
import co.edu.uniquindio.proyecto.model.docs.Cuenta;
import co.edu.uniquindio.proyecto.model.docs.Evento;
import co.edu.uniquindio.proyecto.model.vo.DetalleCarrito;
import co.edu.uniquindio.proyecto.repositories.CarritoRepo;
import co.edu.uniquindio.proyecto.repositories.CuentaRepo;
import co.edu.uniquindio.proyecto.repositories.EventoRepo;
import co.edu.uniquindio.proyecto.services.interfaces.CarritoService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        DetalleCarrito detalle = entradaRepo.save(DetalleCarrito.builder()
                .idEvento(new ObjectId(evento.getId()))
                .nombreLocalidad(agregarAlCarritoDTO.localidad())
                .cantidad(agregarAlCarritoDTO.cantidad())
                .precioUnitario(agregarAlCarritoDTO.localidad())
                .build());

        Carrito carrito = carritoRepo.findByCuenta(cuenta.getId())
                .orElse(Carrito.builder().cuenta(cuenta.getId()).build());

        carrito.getEntradas().add(entrada);
        carritoRepo.save(carrito);

        return carrito.getId();
    }

    @Override
    @Transactional
    public void eliminarDelCarrito(ObjectId idCuenta, ObjectId idEntrada) throws Exception {
        Carrito carrito = carritoRepo.findByCuenta(idCuenta)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        carrito.getEntradas().removeIf(e -> e.getId().equals(idEntrada));
        carritoRepo.save(carrito);
        entradaRepo.deleteById(idEntrada);
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
