package co.edu.uniquindio.proyecto.dto.eventoDTO;

import co.edu.uniquindio.proyecto.model.enums.EstadoEvento;
import co.edu.uniquindio.proyecto.model.enums.TipoEvento;

import java.time.LocalDateTime;

public record ItemEventoDTO(
        String nombre,
        LocalDateTime fecha,
        TipoEvento tipoEvento

) {
}
