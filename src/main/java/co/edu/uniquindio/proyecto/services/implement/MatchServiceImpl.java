package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.Match.MatchDTO;
import co.edu.uniquindio.proyecto.dto.Match.MatchResponseDTO;
import co.edu.uniquindio.proyecto.model.entities.Match;
import co.edu.uniquindio.proyecto.repositories.MatchRepository;
import co.edu.uniquindio.proyecto.services.interfaces.MatchService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    @Override
    public Long crearPartido(MatchDTO dto) {
        Match match = Match.builder()
                .equipoLocal(dto.getEquipoLocal())
                .equipoVisitante(dto.getEquipoVisitante())
                .fechaHora(dto.getFechaHora())
                .estado(Match.EstadoMatch.valueOf(dto.getEstado()))
                .tipoEvento(dto.getTipoEvento())
                .imagenPortada(dto.getImagenPortada())
                .descripcion(dto.getDescripcion())
                .build();
        return matchRepository.save(match).getId();
    }

    @Override
    public void actualizarPartido(Long id, MatchDTO dto) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado"));
        match.setEquipoLocal(dto.getEquipoLocal());
        match.setEquipoVisitante(dto.getEquipoVisitante());
        match.setFechaHora(dto.getFechaHora());
        match.setEstado(Match.EstadoMatch.valueOf(dto.getEstado()));
        match.setTipoEvento(dto.getTipoEvento());
        match.setImagenPortada(dto.getImagenPortada());
        match.setDescripcion(dto.getDescripcion());
        matchRepository.save(match);
    }

    @Override
    public void eliminarPartido(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new EntityNotFoundException("Partido no encontrado");
        }
        matchRepository.deleteById(id);
    }

    @Override
    public MatchResponseDTO obtenerPartido(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Partido no encontrado"));
        return convertir(match);
    }

    @Override
    public List<MatchResponseDTO> listarPartidos() {
        return matchRepository.findAll()
                .stream()
                .map(this::convertir)
                .collect(Collectors.toList());
    }

    private MatchResponseDTO convertir(Match match) {
        return MatchResponseDTO.builder()
                .id(match.getId())
                .equipoLocal(match.getEquipoLocal())
                .equipoVisitante(match.getEquipoVisitante())
                .fechaHora(match.getFechaHora())
                .estado(match.getEstado().name())
                .tipoEvento(match.getTipoEvento())
                .imagenPortada(match.getImagenPortada())
                .descripcion(match.getDescripcion())
                .build();
    }
}
