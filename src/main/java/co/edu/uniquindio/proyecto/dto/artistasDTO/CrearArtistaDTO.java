package co.edu.uniquindio.proyecto.dto.artistasDTO;

import co.edu.uniquindio.proyecto.model.enums.TipoArtista;

import java.util.List;

public record CrearArtistaDTO(

        String nombre,
        List<String> generos,
        TipoArtista tipo,
        String email,
        String telefono,
        double tarifa,
        List<String> referencias,
        String biografia

) {
}
