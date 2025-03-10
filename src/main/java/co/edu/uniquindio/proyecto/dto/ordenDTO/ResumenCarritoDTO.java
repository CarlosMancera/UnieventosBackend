package co.edu.uniquindio.proyecto.dto.ordenDTO;


import java.time.LocalDateTime;

public record ResumenCarritoDTO(

        String nombreEvento,
        LocalDateTime fechaEvento,
        String localidad,
        int cantidad,
        double precioUnitario
) {
}
