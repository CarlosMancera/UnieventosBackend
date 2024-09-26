package co.edu.uniquindio.proyecto.dto.artistasDTO;

import co.edu.uniquindio.proyecto.model.enums.TipoArtista;

import java.util.List;

public record EditarArtistaDTO(

        String id,
        String nombre,
        String genero,
        String email,
        String telefono


) {
}
