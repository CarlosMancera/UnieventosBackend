package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.teamDTO.TeamDTO;
import co.edu.uniquindio.proyecto.dto.teamDTO.TeamResponseDTO;

import java.util.List;

public interface TeamService {
    Long crearEquipo(TeamDTO dto);
    void eliminarEquipo(Long id);
    void actualizarEquipo(Long id, TeamDTO dto);
    TeamResponseDTO obtenerEquipo(Long id);
    List<TeamResponseDTO> listarEquipos();
}