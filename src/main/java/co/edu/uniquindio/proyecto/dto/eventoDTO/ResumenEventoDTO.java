package co.edu.uniquindio.proyecto.dto.eventoDTO;

import co.edu.uniquindio.proyecto.model.enums.EstadoEvento;
import co.edu.uniquindio.proyecto.model.enums.TipoEvento;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ResumenEventoDTO(

        String nombre,
        LocalDateTime fecha,
        String direccion,
        int capacidadTotal,
        TipoEvento tipoEvento,
        EstadoEvento estado
) {
}
