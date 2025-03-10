package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.ordenDTO.AgregarAlCarritoDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.ResumenCarritoDTO;
import co.edu.uniquindio.proyecto.model.entities.Carrito;
import co.edu.uniquindio.proyecto.model.entities.Cuenta;
import co.edu.uniquindio.proyecto.model.entities.Evento;
import co.edu.uniquindio.proyecto.model.entities.Localidad;
import co.edu.uniquindio.proyecto.model.vo.DetalleCarrito;
import co.edu.uniquindio.proyecto.repositories.CarritoRepo;
import co.edu.uniquindio.proyecto.repositories.CuentaRepo;
import co.edu.uniquindio.proyecto.repositories.CuponRepo;
import co.edu.uniquindio.proyecto.repositories.EventoRepo;
import co.edu.uniquindio.proyecto.services.interfaces.CarritoService;
import lombok.RequiredArgsConstructor;
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
    private final CuponRepo cuponRepo;


    //TODO
    @Override
    @Transactional
    public Long agregarAlCarrito(AgregarAlCarritoDTO agregarAlCarritoDTO) throws Exception {
        Cuenta cuenta = cuentaRepo.findById(agregarAlCarritoDTO.idCuenta())
                .orElseThrow(() -> new Exception("Cuenta no encontrada"));

        Evento evento = eventoRepo.findById(Long.valueOf(agregarAlCarritoDTO.idEvento().toString()))
                .orElseThrow(() -> new Exception("Evento no encontrado"));

        double precioUnitario = 0;

        for (Localidad l : evento.getLocalidades()) {
            if (l.getNombre().equals(agregarAlCarritoDTO.localidad())) {
                precioUnitario = l.getPrecio();
                break;
            }
        }

        DetalleCarrito detalle = DetalleCarrito.builder()
                .idEvento(evento.getId())
                .nombreLocalidad(agregarAlCarritoDTO.localidad())
                .cantidad(agregarAlCarritoDTO.cantidad())
                .precioUnitario(precioUnitario)
                .build();
        Carrito carrito = carritoRepo.findByCuenta(cuenta)
                .orElse(
                        Carrito.builder()
                                .cuenta(cuenta)
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
    public void eliminarDelCarrito(Long idCuenta, Long idEvento) throws Exception {
        Cuenta cuenta=new Cuenta();
        Carrito carrito = carritoRepo.findByCuenta(cuenta)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        carrito.getItems().removeIf(e -> e.getIdEvento().equals(idEvento));
        carritoRepo.save(carrito);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumenCarritoDTO> listarCarrito(Long idCuenta) throws Exception {
        Cuenta cuenta=new Cuenta();
        Carrito carrito = (Carrito) carritoRepo.findByCuenta(cuenta)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));
        List<ResumenCarritoDTO> resumenCarritoList = new ArrayList<>();

        for (DetalleCarrito item : carrito.getItems()) {
            ResumenCarritoDTO resumen = convertirAResumenCarritoDTO(item);  // Método personalizado para la conversión
            resumenCarritoList.add(resumen);
        }

        return resumenCarritoList;
    }

    private double calcularSubtotal(Carrito carrito) {
        return carrito.getItems().stream()
                .mapToDouble(e -> e.getPrecioUnitario() * e.getCantidad())
                .sum();


    }

    private ResumenCarritoDTO convertirAResumenCarritoDTO(DetalleCarrito item) {
        // Lógica de conversión del item al DTO

        Evento evento = eventoRepo.findById(item.getIdEvento()).orElseThrow();
        return new ResumenCarritoDTO(
                evento.getNombre(),
                evento.getFecha(),
                item.getNombreLocalidad(),
                item.getCantidad(),
                item.getPrecioUnitario()


        );
    }
}
