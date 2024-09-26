package co.edu.uniquindio.proyecto.dto.ordenDTO;

public record PagoDTO(
        String idOrden,
        String metodoPago,
        String detallesPago,
        String codigoConfirmacion
) {
}
