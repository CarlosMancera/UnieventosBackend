package co.edu.uniquindio.proyecto.dto.ordenDTO;

public record OrdenCompraDTO(
        ObjectId id,
        LocalDateTime fechaCreacion,
        List<ResumenCarritoDTO> entradas,
        double subtotal, double descuento,
        double total
) {
}
