package co.edu.uniquindio.proyecto.dto.cuponDTO;

import co.edu.uniquindio.proyecto.model.enums.TipoCupon;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record EditarCuponDTO(
        String id,
        String codigo,
        @NotNull @Positive
        double descuento,
        @NotNull
        LocalDateTime fechaVencimiento,
        @Positive
        int limiteUso,
        TipoCupon tipoCupon
) {
}
