package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginDTO(
        @NotBlank @Length(max = 40) @Email
        String email,
        @NotBlank @Length(min = 7, max = 20)
        String password
) {
}
