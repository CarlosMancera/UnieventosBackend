package co.edu.uniquindio.proyecto.dto.deseoDTO;

import java.time.LocalDateTime;

public record ResumenDeseoDTO(
        Long id,
        String nombreEvento,
        LocalDateTime fechaEvento,
        boolean recibeInfo
) {
}
