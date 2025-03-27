package co.edu.uniquindio.proyecto.dto.cuentaDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record EditarCuentaDTO(
        @NotNull
        String id,

        @NotBlank
        @Length(max = 80)
        String nombre,

        @NotBlank
        @Length(max = 20)
        String telefono,

        @Length(max = 100)
        String direccion,

        String nuevaContrasena,

        String confirmarContrasena
) {}
