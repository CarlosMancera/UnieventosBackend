package co.edu.uniquindio.proyecto.services.interfaces;
import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.dto.artistasDTO.CrearArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.EditarArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.InformacionArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.ResumenArtistaDTO;

import java.util.List;

public interface ArtistaService {

    List<ResumenArtistaDTO> listarArtistas();
    List<ResumenArtistaDTO> buscarArtistasPorNombre(String nombre);
    String crearArtista(CrearArtistaDTO artista) throws Exception;
    String editarArtista(EditarArtistaDTO artista) throws Exception;
    String eliminarArtista(String id) throws Exception;
    public InformacionArtistaDTO obtenerInformacionArtista(String id) throws Exception;
}








