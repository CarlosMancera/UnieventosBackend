package co.edu.uniquindio.proyecto.dto.ordenDTO;

public record AgregarAlCarritoDTO(
        ObjectId idCuenta,
        ObjectId idEvento,
        String localidad,
        int cantidad
) {
}
