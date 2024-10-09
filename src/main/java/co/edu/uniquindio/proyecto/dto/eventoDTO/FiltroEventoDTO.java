package co.edu.uniquindio.proyecto.dto.eventoDTO;

import co.edu.uniquindio.proyecto.model.enums.TipoEvento;
import java.time.LocalDateTime;

public record FiltroEventoDTO(
        String nombre,
        String ciudad,
        TipoEvento tipoEvento
){


        }