package co.edu.uniquindio.proyecto.dto.cuponDTO;

import co.edu.uniquindio.proyecto.model.enums.EstadoCupon;
import co.edu.uniquindio.proyecto.model.enums.TipoCupon;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record CrearCuponDTO(
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
