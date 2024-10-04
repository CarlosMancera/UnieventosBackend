package co.edu.uniquindio.proyecto.dto.cuponDTO;

import co.edu.uniquindio.proyecto.model.enums.EstadoCupon;
import co.edu.uniquindio.proyecto.model.enums.TipoCupon;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CrearCuponDTO(
        String codigo,
        double descuento,
        @NotNull
        LocalDateTime fechaVencimiento,
        int limiteUso,
        @NotNull
        TipoCupon tipoCupon


) {

}
