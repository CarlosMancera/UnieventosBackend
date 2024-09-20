package co.edu.uniquindio.proyecto.services.implement;


import co.edu.uniquindio.proyecto.dto.ordenDTO.*;
import co.edu.uniquindio.proyecto.model.docs.*;
import co.edu.uniquindio.proyecto.repositories.*;
import co.edu.uniquindio.proyecto.services.interfaces.EmailService;
import co.edu.uniquindio.proyecto.services.interfaces.OrdenService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService{

    private final CarritoRepo carritoRepo;
    private final CuentaRepo cuentaRepo;
    private final EventoRepo eventoRepo;
    private final EntradaRepo entradaRepo;
    private final OrdenRepo ordenRepo;
    private final CuponRepo cuponRepo;
    private final EmailService emailService;

    @Override
    @Transactional
    public String agregarAlCarrito(AgregarAlCarritoDTO agregarAlCarritoDTO) throws Exception {
        Cuenta cuenta = cuentaRepo.findById(agregarAlCarritoDTO.idCuenta())
                .orElseThrow(() -> new Exception("Cuenta no encontrada"));

        Evento evento = eventoRepo.findById(agregarAlCarritoDTO.idEvento())
                .orElseThrow(() -> new Exception("Evento no encontrado"));

        Entrada entrada = entradaRepo.save(Entrada.builder()
                .evento(evento.getId())
                .localidad(agregarAlCarritoDTO.localidad())
                .cantidad(agregarAlCarritoDTO.cantidad())
                .precioUnitario(getPrecioLocalidad(agregarAlCarritoDTO.localidad()))
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
    @Transactional(readOnly = true)
    public List<ResumenCarritoDTO> listarCarrito(ObjectId idCuenta) throws Exception {
        Carrito carrito = carritoRepo.findByCuenta(idCuenta)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        return carrito.getEntradas().stream()
                .map(this::mapToResumenCarritoDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResumenOrdenDTO aplicarDescuento(ObjectId idCuenta, String codigoDescuento) throws Exception {
        Carrito carrito = carritoRepo.findByCuenta(idCuenta)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        Cupon cupon = cuponRepo.findByCodigo(codigoDescuento)
                .orElseThrow(() -> new Exception("Cupón no válido"));

        double subtotal = calcularSubtotal(carrito);
        double descuento = subtotal * (cupon.getPorcentajeDescuento() / 100.0);
        double total = subtotal - descuento;

        return new ResumenOrdenDTO(subtotal, descuento, total);
    }

    @Override
    @Transactional
    public OrdenCompraDTO generarOrdenCompra(ObjectId idCuenta) throws Exception {
        Carrito carrito = carritoRepo.findByCuenta(idCuenta)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        Orden orden = Orden.builder()
                .cuenta(idCuenta)
                .entradas(carrito.getEntradas())
                .fechaCreacion(LocalDateTime.now())
                .estado(EstadoOrden.PENDIENTE)
                .build();

        orden = ordenRepo.save(orden);
        carritoRepo.delete(carrito);

        return mapToOrdenCompraDTO(orden);
    }

    @Override
    @Transactional
    public PagoDTO realizarPago(PagoDTO pagoDTO) throws Exception {
        Orden orden = ordenRepo.findById(pagoDTO.idOrden())
                .orElseThrow(() -> new Exception("Orden no encontrada"));

        // Aquí iría la lógica para procesar el pago con la pasarela de pagos

        orden.setEstado(EstadoOrden.PAGADA);
        ordenRepo.save(orden);

        String codigoConfirmacion = generarCodigoConfirmacion();
        enviarCodigoConfirmacion(orden.getCuenta(), codigoConfirmacion);

        return new PagoDTO(orden.getId(), pagoDTO.metodoPago(), pagoDTO.detallesPago(), codigoConfirmacion);
    }

    @Override
    @Transactional
    public void confirmarPago(ObjectId idOrden, String codigoConfirmacion) throws Exception {
        Orden orden = ordenRepo.findById(idOrden)
                .orElseThrow(() -> new Exception("Orden no encontrada"));

        if (!codigoConfirmacion.equals(orden.getCodigoConfirmacion())) {
            throw new Exception("Código de confirmación inválido");
        }

        orden.setEstado(EstadoOrden.CONFIRMADA);
        ordenRepo.save(orden);

        // Aplicar descuento adicional si es la primera compra
        if (esPrimeraCompra(orden.getCuenta())) {
            aplicarDescuentoPrimeraCompra(orden);
        }
    }

    // Métodos auxiliares

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

    private OrdenCompraDTO mapToOrdenCompraDTO(Orden orden) {
        return new OrdenCompraDTO(
                orden.getId(),
                orden.getFechaCreacion(),
                orden.getEntradas().stream().map(this::mapToResumenCarritoDTO).collect(Collectors.toList()),
                calcularSubtotal(orden),
                calcularDescuento(orden),
                calcularTotal(orden)
        );
    }

    private double getPrecioLocalidad(String localidad) {
        // Implementar lógica para obtener el precio según la localidad
        return switch (localidad) {
            case "VIP" -> 80000.00;
            case "Platea" -> 60000.00;
            case "General" -> 50000.00;
            default -> throw new IllegalArgumentException("Localidad no válida");
        };
    }

    private double calcularSubtotal(Carrito carrito) {
        return carrito.getEntradas().stream()
                .mapToDouble(e -> e.getPrecioUnitario() * e.getCantidad())
                .sum();
    }

    private double calcularSubtotal(Orden orden) {
        return orden.getEntradas().stream()
                .mapToDouble(e -> e.getPrecioUnitario() * e.getCantidad())
                .sum();
    }

    private double calcularDescuento(Orden orden) {
        // Implementar lógica para calcular el descuento
        return 0.0;
    }

    private double calcularTotal(Orden orden) {
        return calcularSubtotal(orden) - calcularDescuento(orden);
    }

    private String generarCodigoConfirmacion() {
        // Implementar lógica para generar un código de confirmación único
        return "CODE" + System.currentTimeMillis();
    }

    private void enviarCodigoConfirmacion(ObjectId idCuenta, String codigoConfirmacion) {
        Cuenta cuenta = cuentaRepo.findById(idCuenta).orElseThrow();
        emailService.enviarCorreo(cuenta.getEmail(), "Código de confirmación",
                "Su código de confirmación es: " + codigoConfirmacion);
    }

    private boolean esPrimeraCompra(ObjectId idCuenta) {
        // Implementar lógica para verificar si es la primera compra del usuario
        return ordenRepo.countByCuenta(idCuenta) == 1;
    }

    private void aplicarDescuentoPrimeraCompra(Orden orden) {
        // Implementar lógica para aplicar el descuento del 15% por primera compra
        double descuentoAdicional = calcularSubtotal(orden) * 0.15;
        // Actualizar el total de la orden con el descuento adicional
    }
}
