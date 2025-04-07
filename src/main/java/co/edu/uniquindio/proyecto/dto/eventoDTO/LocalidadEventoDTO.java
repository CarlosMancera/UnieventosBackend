package co.edu.uniquindio.proyecto.dto.eventoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record LocalidadEventoDTO(
        @NotBlank @Length(max = 50)
        String nombre,

        @NotNull
        double precio,

        @NotNull
        int capacidad,

        @NotBlank
        String descripcion,

        @NotBlank
        String imagen
) {
}
