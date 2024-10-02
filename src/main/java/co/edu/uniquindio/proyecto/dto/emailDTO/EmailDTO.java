package co.edu.uniquindio.proyecto.dto.emailDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmailDTO(
        @NotBlank @NotNull @Email
        String destinatario,
        @NotBlank @NotNull
        String asunto,
        @NotBlank @NotNull
        String cuerpo

) {
}
