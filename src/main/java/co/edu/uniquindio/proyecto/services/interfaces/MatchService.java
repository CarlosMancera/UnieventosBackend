package co.edu.uniquindio.proyecto.services.interfaces;


import co.edu.uniquindio.proyecto.dto.Match.MatchDTO;
import co.edu.uniquindio.proyecto.dto.Match.MatchResponseDTO;

import java.util.List;

public interface MatchService {
    Long crearPartido(MatchDTO dto);
    void actualizarPartido(Long id, MatchDTO dto);
    void eliminarPartido(Long id);
    MatchResponseDTO obtenerPartido(Long id);
    List<MatchResponseDTO> listarPartidos();
}
