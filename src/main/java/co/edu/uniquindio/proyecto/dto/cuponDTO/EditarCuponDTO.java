package co.edu.uniquindio.proyecto.dto.cuponDTO;

import co.edu.uniquindio.proyecto.model.enums.TipoCupon;

import java.time.LocalDateTime;

public record EditarCuponDTO(
        String id,
        String codigo,
        double descuento,
        LocalDateTime fecha_Vencimiento,
        int limiteUso,
        TipoCupon tipoCupon
) {
}
