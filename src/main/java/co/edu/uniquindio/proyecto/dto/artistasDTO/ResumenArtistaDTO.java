package co.edu.uniquindio.proyecto.dto.artistasDTO;

import co.edu.uniquindio.proyecto.model.enums.EstadoArtista;
import co.edu.uniquindio.proyecto.model.enums.TipoArtista;

import java.util.List;

public record ResumenArtistaDTO<tipo>(

        String nombre,
        List<String> generos,
        EstadoArtista estado,
        String email,
        String telefono,
        TipoArtista tipo
) {
}
