package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.teamDTO.TeamDTO;
import co.edu.uniquindio.proyecto.dto.teamDTO.TeamResponseDTO;
import co.edu.uniquindio.proyecto.model.entities.Team;
import co.edu.uniquindio.proyecto.repositories.TeamRepository;
import co.edu.uniquindio.proyecto.services.interfaces.TeamService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public Long crearEquipo(TeamDTO dto) {
        Team team = Team.builder()
                .nombre(dto.getNombre())
                .ciudad(dto.getCiudad())
                .escudoUrl(dto.getEscudoUrl())
                .entrenador(dto.getEntrenador())
                .build();
        return teamRepository.save(team).getId();
    }

    @Override
    public void eliminarEquipo(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new EntityNotFoundException("Equipo no encontrado");
        }
        teamRepository.deleteById(id);
    }

    @Override
    public void actualizarEquipo(Long id, TeamDTO dto) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));
        team.setNombre(dto.getNombre());
        team.setCiudad(dto.getCiudad());
        team.setEscudoUrl(dto.getEscudoUrl());
        team.setEntrenador(dto.getEntrenador());
        teamRepository.save(team);
    }

    @Override
    public TeamResponseDTO obtenerEquipo(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));
        return convertir(team);
    }

    @Override
    public List<TeamResponseDTO> listarEquipos() {
        return teamRepository.findAll()
                .stream()
                .map(this::convertir)
                .collect(Collectors.toList());
    }

    private TeamResponseDTO convertir(Team team) {
        return TeamResponseDTO.builder()
                .id(team.getId())
                .nombre(team.getNombre())
                .ciudad(team.getCiudad())
                .escudoUrl(team.getEscudoUrl())
                .entrenador(team.getEntrenador())
                .build();
    }
}

