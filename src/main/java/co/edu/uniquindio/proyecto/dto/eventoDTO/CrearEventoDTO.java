package co.edu.uniquindio.proyecto.dto.eventoDTO;

import co.edu.uniquindio.proyecto.model.enums.TipoEvento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record CrearEventoDTO(

        @NotBlank @Length(max = 50)
        String nombre,

        @NotBlank
        String artista, // Este es el ID del artista

        @NotBlank @Length(max = 100)
        String descripcion,

        @NotNull
        LocalDateTime fecha,

        @Length(max = 80)
        String direccion,

        @NotBlank @Length(max = 50)
        String ciudad,

        TipoEvento tipoEvento,

        @NotBlank
        String imagenPortada,

        @NotBlank
        String imagenLocalidades,

        @NotNull
        List<LocalidadEventoDTO> localidades

) {}
