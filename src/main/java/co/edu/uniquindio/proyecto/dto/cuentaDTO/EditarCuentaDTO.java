package co.edu.uniquindio.proyecto.dto.cuentaDTO;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record EditarCuentaDTO(
        String id,
        @NotBlank @Length(max = 10)
        String nombre,
        @NotBlank @Length(max = 10)
        String telefono,
        @Length(max = 80)
        String direccion

) {
}
