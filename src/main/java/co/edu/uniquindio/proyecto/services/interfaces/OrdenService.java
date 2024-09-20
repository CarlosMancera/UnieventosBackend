package co.edu.uniquindio.proyecto.services.interfaces;

public interface OrdenService {
    String agregarAlCarrito(AgregarAlCarritoDTO agregarAlCarritoDTO) throws Exception;
    void eliminarDelCarrito(ObjectId idCuenta, ObjectId idEntrada) throws Exception;
    List<ResumenCarritoDTO> listarCarrito(ObjectId idCuenta) throws Exception;
    ResumenOrdenDTO aplicarDescuento(ObjectId idCuenta, String codigoDescuento) throws Exception;
    OrdenCompraDTO generarOrdenCompra(ObjectId idCuenta) throws Exception;
    PagoDTO realizarPago(PagoDTO pagoDTO) throws Exception;
    void confirmarPago(ObjectId idOrden, String codigoConfirmacion) throws Exception;

}
