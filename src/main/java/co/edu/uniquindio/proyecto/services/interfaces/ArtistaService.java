package co.edu.uniquindio.proyecto.services.interfaces;
import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.dto.artistasDTO.CrearArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.EditarArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.InformacionArtistaDTO;

import java.util.List;

public interface ArtistaService {

    List<InformacionArtistaDTO> listarArtistas() throws Exception;
    List<InformacionArtistaDTO> buscarArtistasPorNombre(String nombre);
    InformacionArtistaDTO obtenerInformacionArtista(String id) throws Exception;
    String eliminarArtista(String id) throws Exception;
    String editarArtista(EditarArtistaDTO artista) throws Exception;
    String crearArtista(CrearArtistaDTO artista) throws Exception;

    //crearArtista



}








