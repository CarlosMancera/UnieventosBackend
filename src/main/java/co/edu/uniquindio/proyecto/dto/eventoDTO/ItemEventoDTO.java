package co.edu.uniquindio.proyecto.dto.eventoDTO;

import co.edu.uniquindio.proyecto.model.enums.EstadoEvento;

import java.time.LocalDateTime;

public record ItemEventoDTO(
        String nombre,
        LocalDateTime fecha,
        EstadoEvento estadoEvento

) {
}
