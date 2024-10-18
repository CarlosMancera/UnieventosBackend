package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CambiarPasswordDTO(
        @NotBlank @Length(max = 40) @Email
        String email,
        String codigoVerificacion,
        @NotBlank @Length(max = 40)
        String passwordNuevaT
) {
}
