package co.edu.uniquindio.proyecto.dto.eventoDTO;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LocalidadEventoDTO(
        @NotBlank @Length(max = 50)
        String nombre,
        double precio,
        int capacidad,
        String imagen
) {
}
