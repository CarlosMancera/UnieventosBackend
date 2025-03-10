package co.edu.uniquindio.proyecto.services.implement;


import co.edu.uniquindio.proyecto.dto.cuponDTO.ResumenCuponDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.*;
import co.edu.uniquindio.proyecto.model.entities.*;
import co.edu.uniquindio.proyecto.model.vo.DetalleCarrito;
import co.edu.uniquindio.proyecto.model.vo.Pago;
import co.edu.uniquindio.proyecto.repositories.*;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService{

    private final CarritoRepo carritoRepo;
    private final EventoRepo eventoRepo;
    private final OrdenRepo ordenRepo;
    private final CuponRepo cuponRepo;


    @Override
    @Transactional
    public ResumenOrdenDTO aplicarDescuento(Long idCuenta, String codigoDescuento) throws Exception {
        Cuenta cuenta=new Cuenta();

        Carrito carrito = (Carrito) carritoRepo.findByCuenta(cuenta)
                .orElseThrow(() -> new Exception("Carrito no encontrado"));

        Cupon cupon = cuponRepo.findByCodigo(codigoDescuento)
                .orElseThrow(() -> new Exception("Cupón no válido"));

        //METODO PARA VALIDAR USO DEL CUPON
        if(cupon.getCantidadUsados()>cupon.getLimiteUso()){

            throw new Exception("El cupón ha superado su límite de uso");

        } else if (cupon.getFechaVencimiento().isBefore(LocalDateTime.now())) {
            throw new Exception("El cupón ha excedido la fecha de vencimiento");
        }

        //Se suma la cantidad de cupones usados en 1
        cupon.setCantidadUsados(cupon.getCantidadUsados()+1);

        double subtotal = calcularSubtotal(carrito);
        double descuento = subtotal * (cupon.getDescuento() / 100.0);
        double total = subtotal - descuento;

        return new ResumenOrdenDTO(subtotal, descuento, total);
    }

    @Override
    @Transactional
    public OrdenCompraDTO generarOrdenCompra(Long idCuenta) throws Exception {
        Cuenta cuenta=new Cuenta();

        Optional<Object> carritoOptional = Optional.ofNullable(carritoRepo.findByCuenta(cuenta));

        if (carritoOptional.isEmpty()) {
            throw new Exception("Carrito no encontrado");
        }
        Cuenta cliente=new Cuenta();
        Carrito carrito = (Carrito) carritoOptional.get();

        OrdenCompra orden = new OrdenCompra();
        orden.setCliente(cliente);
        orden.setItems(convertirADetalleOrden(carrito.getItems()));
        orden.setFecha(LocalDateTime.now());
        orden.getPago().setEstado("PENDIENTE");

        orden = ordenRepo.save(orden);
        carritoRepo.delete(carrito);

        return mapToOrdenCompraDTO(orden);
    }

    @Override
    public Preference realizarPago(Long idOrden) throws Exception {

        // Obtener la orden guardada en la base de datos y los ítems de la orden
        Optional<OrdenCompra> optionalOrdenCompra = ordenRepo.findById(idOrden);
        if(optionalOrdenCompra.isEmpty()) {
            throw new Exception("Orden no encontrada");
        }

        OrdenCompra ordenGuardada = optionalOrdenCompra.get();
        List<PreferenceItemRequest> itemsPasarela = new ArrayList<>();


        // Recorrer los items de la orden y crea los ítems de la pasarela
        for (DetalleOrden item : ordenGuardada.getItems()) {

            // Obtener el evento y la localidad del ítem
            Optional<Evento> eventOptional = eventoRepo.findById(item.getEvento().getId());

            if (eventOptional.isEmpty()) {
                throw new Exception("Carrito no encontrado");
            }
            Evento evento = eventOptional.get();
            Localidad localidad = evento.getLocalidades().get(0);


            // Crear el item de la pasarela
            PreferenceItemRequest itemRequest =
                    PreferenceItemRequest.builder()
                            .id(evento.getId().toString())
                            .title(evento.getNombre())
                            .pictureUrl(evento.getImagen())
                            .categoryId(evento.getTipoEvento().name())
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
                .notificationUrl("https://bd19-189-50-209-152.ngrok-free.app/api/public/orden/recibir-notificacion")
                .build();


        // Crear la preferencia en la pasarela de MercadoPago
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);


        // Guardar el código de la pasarela en la orden
        ordenGuardada.setCodigoPasarela(preference.getId());    //TODO ESTE CÓDIGO DE PASARELA ES EL CÓDIGO
        ordenRepo.save(ordenGuardada);                          //DE AUTORIZACIÓN DEL PAGO?


        return preference;
    }

    @Override
    @Transactional
    public void confirmarPago(Long idOrden, String codigoConfirmacion) throws Exception {
        OrdenCompra orden = ordenRepo.findById(idOrden)
                .orElseThrow(() -> new Exception("Orden no encontrada"));

        if (!codigoConfirmacion.equals(orden.getPago().getCodigoAutorizacion())) {
            throw new Exception("Código de confirmación inválido");
        }

        orden.getPago().setEstado("CONFIRMADO");
        ordenRepo.save(orden);

        // Aplicar descuento adicional si es la primera compra
        if (esPrimeraCompra(orden.getCliente().getId())) {
            aplicarDescuentoPrimeraCompra(orden);
        }
    }

    @Override
    public List<ResumenCuponDTO> buscarCuponesUtilizadosPorUsuario(Long idUsuario) throws Exception {
        return List.of();
    }


    private double calcularSubtotal(Carrito carrito) {
        double subtotal = 0.0;
        List<DetalleCarrito> detalles = carrito.getItems();

        for (DetalleCarrito detalle : detalles) {
            double precioUnitario = detalle.getPrecioUnitario();
            int cantidad = detalle.getCantidad();
            subtotal += precioUnitario * cantidad;
        }

        return subtotal;
    }

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
                OrdenCompra orden = ordenRepo.findById(1L)
                        .orElseThrow(() -> new Exception("Orden no encontrada"));
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
        pago.setFechaPago( payment.getDateCreated().toLocalDateTime() );
        pago.setEstado(payment.getStatus());
        pago.setCodigoAutorizacion(payment.getAuthorizationCode());
        pago.setValorTransaccion(payment.getTransactionAmount().floatValue());
        return pago;
    }


    //_-----------------------AUX------------------------

    private OrdenCompraDTO mapToOrdenCompraDTO(OrdenCompra orden) throws Exception{
        String id = String.valueOf(orden.getId());
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

    private ResumenCarritoDTO mapToResumenCarritoDTO(DetalleOrden detalle) throws Exception {
        Evento evento = eventoRepo.findById(detalle.getEvento().getId())
                .orElseThrow(() -> new Exception("Orden no encontrada"));

        return new ResumenCarritoDTO(evento.getNombre(),evento.getFecha()
                ,detalle.getNombreLocalidad(),detalle.getCantidad()
                ,detalle.getPrecioUnitario());

    }

    private double calcularDescuento(OrdenCompra orden) throws Exception {
        Optional<Cupon> cuponOp = cuponRepo.findByCodigo(orden.getCupon().toString());
        if(cuponOp.isEmpty()){
            throw new Exception();
        }
        Cupon cupon = cuponOp.get();
        double porcentaje = cupon.getDescuento();
        return orden.getTotal()*porcentaje/100;
    }

    private double calcularTotal(OrdenCompra orden) throws Exception {
        return calcularSubtotal(orden) - calcularDescuento(orden);
    }

    private void aplicarDescuentoPrimeraCompra(OrdenCompra orden) {
        // Implementar lógica para aplicar el descuento del 15% por primera compra

        double descuentoAdicional = calcularSubtotal(orden) * 0.15;
        // Actualizar el total de la orden con el descuento adicional
    }

    private boolean esPrimeraCompra(Long idCuenta) {
        // Implementar lógica para verificar si es la primera compra del usuario
        return true;
    }

    private List<DetalleOrden> convertirADetalleOrden(List<DetalleCarrito> items) {

        ArrayList<DetalleOrden> itemsOrden = new ArrayList<>();

/*
        for (DetalleCarrito item : items) {

            DetalleOrden itemOrden = DetalleOrden.builder().nombreLocalidad(item.getNombreLocalidad())
                            .idEvento(item.getIdEvento()).precioUnitario(item.getPrecioUnitario()).cantidad(item.getCantidad()).build();
            itemsOrden.add(itemOrden);

        }
*/

        return itemsOrden;

    }
}
