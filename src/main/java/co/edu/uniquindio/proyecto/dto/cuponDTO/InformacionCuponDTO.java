package co.edu.uniquindio.proyecto.dto.cuponDTO;

import co.edu.uniquindio.proyecto.model.enums.EstadoCupon;
import co.edu.uniquindio.proyecto.model.enums.TipoCupon;

import java.time.LocalDateTime;

public record InformacionCuponDTO(

        String codigo,
        double descuento,
        LocalDateTime fechaVencimiento,
        int limiteUso,
        TipoCupon tipoCupon,
        EstadoCupon estadoCupon
) {
}
