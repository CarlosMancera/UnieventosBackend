package co.edu.uniquindio.proyecto.services.interfaces;
import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.dto.artistasDTO.ResumenArtistaDTO;

import java.util.List;

public interface ArtistaService {

    List<ResumenArtistaDTO> listarArtistas();
    List<ResumenArtistaDTO> buscarArtistasPorNombre(String nombre);

}








