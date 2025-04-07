package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.artistasDTO.CrearArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.EditarArtistaDTO;
import co.edu.uniquindio.proyecto.dto.artistasDTO.InformacionArtistaDTO;
import co.edu.uniquindio.proyecto.model.entities.Artista;

import java.util.List;
import java.util.Optional;

public interface ArtistaService {

    List<InformacionArtistaDTO> listarArtistas() throws Exception;

    List<InformacionArtistaDTO> buscarArtistasPorNombre(String nombre);

    InformacionArtistaDTO obtenerInformacionArtista(Long id) throws Exception;

    String eliminarArtista(Long id) throws Exception;

    String editarArtista(EditarArtistaDTO artista) throws Exception;

    String crearArtista(CrearArtistaDTO artista) throws Exception;


    Artista findById(Long id) throws Exception;
}
