package co.edu.uniquindio.proyecto.dto.cuponDTO;

import co.edu.uniquindio.proyecto.model.enums.EstadoCupon;

import java.time.LocalDateTime;

public record ResumenCuponDTO(

        String codigo,
        double descuento,
        LocalDateTime fechaVencimiento,
        EstadoCupon estadoCupon
) {
}
