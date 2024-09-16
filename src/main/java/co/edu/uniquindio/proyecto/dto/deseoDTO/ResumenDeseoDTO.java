package co.edu.uniquindio.proyecto.dto.deseoDTO;

import java.time.LocalDateTime;

public record ResumenDeseoDTO(
        String id,
        String nombreEvento,
        LocalDateTime fechaEvento,
        boolean recibeInfo
) {
}
