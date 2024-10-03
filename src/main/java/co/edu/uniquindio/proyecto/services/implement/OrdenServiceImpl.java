package co.edu.uniquindio.proyecto.services.implement;


import co.edu.uniquindio.proyecto.dto.cuponDTO.ResumenCuponDTO;
import co.edu.uniquindio.proyecto.dto.emailDTO.EmailDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.*;
import co.edu.uniquindio.proyecto.model.docs.*;
import co.edu.uniquindio.proyecto.model.vo.DetalleCarrito;
import co.edu.uniquindio.proyecto.model.vo.DetalleOrden;
import co.edu.uniquindio.proyecto.model.vo.Localidad;
import co.edu.uniquindio.proyecto.model.vo.Pago;
import co.edu.uniquindio.proyecto.repositories.*;
import co.edu.uniquindio.proyecto.services.interfaces.EmailService;
import co.edu.uniquindio.proyecto.services.interfaces.OrdenService;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService{

    private final CarritoRepo carritoRepo;
    private final CuentaRepo cuentaRepo;
    private final EventoRepo eventoRepo;
    private final OrdenRepo ordenRepo;
    private final CuponRepo cuponRepo;
    private final EmailService emailService;


  /*
    public List<ResumenCarritoDTO> listarCarrito(ObjectId idCuenta) throws Exception {
        Carrito carrito = carritoRepo.findByCuenta(idCuenta)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));


    }*/
     @Override
    @Transactional(readOnly = true)
    public List<ResumenCarritoDTO> listarCarrito(ObjectId idCuenta) throws Exception {
        Carrito carrito = carritoRepo.findByCuenta(idCuenta)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        List<ResumenCarritoDTO> resumenCarritoList = new ArrayList<>();

        for (DetalleCarrito item : carrito.getItems()) {
            ResumenCarritoDTO resumen = convertirAResumenCarritoDTO(item);  // Método personalizado para la conversión
            resumenCarritoList.add(resumen);
        }

        return resumenCarritoList;
    }

    private ResumenCarritoDTO convertirAResumenCarritoDTO(DetalleCarrito item) {
        // Lógica de conversión del item al DTO
        return new ResumenCarritoDTO(
                eventoRepo.findById(item.getIdEvento()).get().getNombre(),
                item.getNombreLocalidad(),
                item.getCantidad() * item.getPrecioUnitario(),

                // otros campos...
        );
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

    /*@Override
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
    }*/

    @Override
    @Transactional
    public OrdenCompraDTO generarOrdenCompra(ObjectId idCuenta) throws Exception {
        Optional<Carrito> carritoOptional = carritoRepo.findByCuenta(idCuenta);

        if (!carritoOptional.isPresent()) {
            throw new Exception("Carrito no encontrado");
        }

        Carrito carrito = carritoOptional.get();

        OrdenCompra orden = new OrdenCompra();
        orden.setCuenta(idCuenta);
        orden.setEntradas(carrito.getEntradas());
        orden.setFechaCreacion(LocalDateTime.now());
        orden.setEstado(EstadoOrden.PENDIENTE);

        orden = ordenRepo.save(orden);
        carritoRepo.delete(carrito);

        return mapToOrdenCompraDTO(orden);
    }

    @Override
    public Preference realizarPago(String idOrden) throws Exception {


        // Obtener la orden guardada en la base de datos y los ítems de la orden
        OrdenCompra ordenGuardada = obtenerOrden(idOrden);
        List<PreferenceItemRequest> itemsPasarela = new ArrayList<>();


        // Recorrer los items de la orden y crea los ítems de la pasarela
        for (DetalleOrden item : ordenGuardada.getDetalle()) {


            // Obtener el evento y la localidad del ítem
            Evento evento = eventoService.obtenerEvento(item.getCodigoEvento().toString());
            Localidad localidad = evento.obtenerLocalidad(item.getNombreLocalidad());


            // Crear el item de la pasarela
            PreferenceItemRequest itemRequest =
                    PreferenceItemRequest.builder()
                            .id(evento.getCodigo())
                            .title(evento.getNombre())
                            .pictureUrl(evento.getImagenPortada())
                            .categoryId(evento.getTipo().name())
                            .quantity(item.getCantidad())
                            .currencyId("COP")
                            .unitPrice(BigDecimal.valueOf(localidad.getPrecio()))
                            .build();


            itemsPasarela.add(itemRequest);
        }


        // Configurar las credenciales de MercadoPago
        MercadoPagoConfig.setAccessToken("ACCESS_TOKEN");


        // Configurar las urls de retorno de la pasarela (Frontend)
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("URL PAGO EXITOSO")
                .failure("URL PAGO FALLIDO")
                .pending("URL PAGO PENDIENTE")
                .build();


        // Construir la preferencia de la pasarela con los ítems, metadatos y urls de retorno
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .backUrls(backUrls)
                .items(itemsPasarela)
                .metadata(Map.of("id_orden", ordenGuardada.getCodigo()))
                .notificationUrl("URL NOTIFICACION")
                .build();


        // Crear la preferencia en la pasarela de MercadoPago
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);


        // Guardar el código de la pasarela en la orden
        ordenGuardada.setCodigoPasarela(preference.getId());
        ordenRepo.save(ordenGuardada);


        return preference;
    }

    @Override
    @Transactional
    public void confirmarPago(ObjectId idOrden, String codigoConfirmacion) throws Exception {
        OrdenCompra orden = ordenRepo.findById(idOrden)
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

    @Override
    public List<ResumenCuponDTO> buscarCuponesUtilizadosPorUsuario(String idUsuario) throws Exception {
        return List.of();
    }



    // Métodos auxiliares



   /* private OrdenCompraDTO mapToOrdenCompraDTO(Orden orden) {
        return new OrdenCompraDTO(
                orden.getId(),
                orden.getFechaCreacion(),
                orden.getEntradas().stream().map(this::mapToResumenCarritoDTO).collect(Collectors.toList()),
                calcularSubtotal(orden),
                calcularDescuento(orden),
                calcularTotal(orden)
        );
    }*/

    private OrdenCompraDTO mapToOrdenCompraDTO(OrdenCompra orden) {
        String id = orden.getId();
        LocalDateTime fechaCreacion = orden.getFecha();
        List<DetalleOrden> items = orden.getItems();
        List<ResumenCarritoDTO> resumenItems = new ArrayList<>();

        for (DetalleOrden detalle : items) {
            ResumenCarritoDTO resumenEntrada = mapToResumenCarritoDTO(detalle);
            resumenItems.add(resumenEntrada);
        }

        double subtotal = calcularSubtotal(orden);
        double descuento = calcularDescuento(orden);
        double total = calcularTotal(orden);

        return new OrdenCompraDTO(
                id,
                fechaCreacion,
                resumenItems,
                subtotal,
                descuento,
                total
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

    /*private double calcularSubtotal(Orden orden) {
        return orden.getEntradas().stream()
                .mapToDouble(e -> e.getPrecioUnitario() * e.getCantidad())
                .sum();
    }*/

    private double calcularSubtotal(OrdenCompra orden) {
        double subtotal = 0.0;
        List<DetalleOrden> detalles = orden.getItems();

        for (DetalleOrden detalle : detalles) {
            double precioUnitario = detalle.getPrecioUnitario();
            int cantidad = detalle.getCantidad();
            subtotal += precioUnitario * cantidad;
        }

        return subtotal;
    }

    @Override
    public void recibirNotificacionMercadoPago(Map<String, Object> request) throws Exception {
        try {


            // Obtener el tipo de notificación
            Object tipo = request.get("type");


            // Si la notificación es de un pago entonces obtener el pago y la orden asociada
            if ("payment".equals(tipo)) {


                // Capturamos el JSON que viene en el request y lo convertimos a un String
                String input = request.get("data").toString();


                // Extraemos los números de la cadena, es decir, el id del pago
                String idPago = input.replaceAll("\\D+", "");


                // Se crea el cliente de MercadoPago y se obtiene el pago con el id
                PaymentClient client = new PaymentClient();
                Payment payment = client.get( Long.parseLong(idPago) );


                // Obtener el id de la orden asociada al pago que viene en los metadatos
                String idOrden = payment.getMetadata().get("id_orden").toString();


                // Se obtiene la orden guardada en la base de datos y se le asigna el pago
                OrdenCompra orden = obtenerOrden(idOrden);
                Pago pago = crearPago(payment);
                orden.setPago(pago);
                ordenRepo.save(orden);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Pago crearPago(Payment payment) {
        Pago pago = new Pago();
        pago.setCodigoAutorizacion(payment.getId().toString());
        pago.setFecha( payment.getDateCreated().toLocalDateTime() );
        pago.setEstado(payment.getStatus());
        pago.setCodigoAutorizacion(payment.getAuthorizationCode());
        pago.setValorTransaccion(payment.getTransactionAmount().floatValue());
        return pago;
    }


    //_-----------------------AUX------------------------

    private double calcularDescuento(OrdenCompra orden) {
        // Implementar lógica para calcular el descuento
        return 0.0;
    }

    private double calcularTotal(OrdenCompra orden) {
        return calcularSubtotal(orden) - calcularDescuento(orden);
    }

    private String generarCodigoConfirmacion() {
        // Implementar lógica para generar un código de confirmación único
        return "CODE" + System.currentTimeMillis();
    }

    private void enviarCodigoConfirmacion(ObjectId idCuenta, String codigoConfirmacion) throws Exception {
        Cuenta cuenta = cuentaRepo.findById(idCuenta.toString()).orElseThrow();
        emailService.enviarCorreo(new EmailDTO("Código de confirmación", "Su código de confirmación es: " + codigoConfirmacion, cuenta.getEmail()));
    }

    private void aplicarDescuentoPrimeraCompra(OrdenCompra orden) {
        // Implementar lógica para aplicar el descuento del 15% por primera compra
        double descuentoAdicional = calcularSubtotal(orden) * 0.15;
        // Actualizar el total de la orden con el descuento adicional
    }

    private boolean esPrimeraCompra(ObjectId idCuenta) {
        // Implementar lógica para verificar si es la primera compra del usuario
        return ordenRepo.countByCuenta(idCuenta.toString()) == 1;
    }
}
