package co.edu.uniquindio.proyecto.dto;

import java.time.LocalDateTime;

public record ItemEventoDTO(
        String url,
        LocalDateTime fecha,
        String nombre,
        String direccion,
        boolean disponibilidad

) {
}
