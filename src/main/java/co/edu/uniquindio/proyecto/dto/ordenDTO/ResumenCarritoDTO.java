package co.edu.uniquindio.proyecto.dto.ordenDTO;

public record ResumenCarritoDTO(
        ObjectId id,
        String nombreEvento,
        LocalDateTime fechaEvento,
        String localidad, int cantidad,
        double precioUnitario
) {
}
