package co.edu.uniquindio.proyecto.dto.ordenDTO;

public record PagoDTO(
        ObjectId idOrden,
        String metodoPago,
        String detallesPago,
        String codigoConfirmacion
) {
}
