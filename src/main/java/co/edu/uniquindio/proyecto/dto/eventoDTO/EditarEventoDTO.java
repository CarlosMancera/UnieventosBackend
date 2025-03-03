package co.edu.uniquindio.proyecto.dto.eventoDTO;

import co.edu.uniquindio.proyecto.model.enums.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record EditarEventoDTO(
        String id,
        @NotBlank @Length(max = 50)
        String nombre,
        @NotBlank
        String artista,
        @NotBlank @Length(max = 100)
        String descripcion,
        @NotNull
        LocalDateTime fecha,
        @Length(max = 80)
        String direccion,
        @NotBlank @Length(max = 50)
        String ciudad,
        TipoEvento tipoEvento,
        List<LocalidadEventoDTO> localidades
) {}