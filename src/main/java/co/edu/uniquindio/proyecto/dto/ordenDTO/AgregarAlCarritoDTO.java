package co.edu.uniquindio.proyecto.dto.ordenDTO;


public record AgregarAlCarritoDTO(
		Long idCuenta,
        Long idEvento,
        String localidad,
        int cantidad
) {
}
