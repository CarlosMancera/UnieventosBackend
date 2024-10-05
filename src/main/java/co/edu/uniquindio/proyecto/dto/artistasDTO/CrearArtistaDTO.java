package co.edu.uniquindio.proyecto.dto.artistasDTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CrearArtistaDTO(

        @NotBlank @Length (max = 50)
        String nombre,
        @NotBlank @Length (max = 20)
        String genero,
        @NotBlank @Length(max = 40) @Email
        String email,
        @NotBlank @Length (max = 10)
        String telefono

) {
}
