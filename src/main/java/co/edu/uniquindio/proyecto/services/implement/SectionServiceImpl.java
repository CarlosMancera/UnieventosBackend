package co.edu.uniquindio.proyecto.services.implement;


import co.edu.uniquindio.proyecto.dto.sectionDTO.SectionDTO;
import co.edu.uniquindio.proyecto.dto.sectionDTO.SectionResponseDTO;
import co.edu.uniquindio.proyecto.model.entities.Match;
import co.edu.uniquindio.proyecto.model.entities.Section;
import co.edu.uniquindio.proyecto.repositories.MatchRepository;
import co.edu.uniquindio.proyecto.repositories.SectionRepository;
import co.edu.uniquindio.proyecto.services.interfaces.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;
    private final MatchRepository matchRepository;

    @Override
    public Long crearSection(SectionDTO dto) {
        Match match = matchRepository.findById(dto.getMatchId()).orElseThrow();
        Section section = Section.builder()
                .nombre(dto.getNombre())
                .capacidadTotal(dto.getCapacidadTotal())
                .precio(dto.getPrecio())
                .match(match)
                .build();
        return sectionRepository.save(section).getId();
    }

    @Override
    public void actualizarSection(Long id, SectionDTO dto) {
        Section section = sectionRepository.findById(id).orElseThrow();
        section.setNombre(dto.getNombre());
        section.setCapacidadTotal(dto.getCapacidadTotal());
        section.setPrecio(dto.getPrecio());
        sectionRepository.save(section);
    }

    @Override
    public void eliminarSection(Long id) {
        sectionRepository.deleteById(id);
    }

    @Override
    public SectionResponseDTO obtenerSection(Long id) {
        Section section = sectionRepository.findById(id).orElseThrow();
        return mapToResponse(section);
    }

    @Override
    public List<SectionResponseDTO> listarSectionsPorMatch(Long matchId) {
        return sectionRepository.findAllByMatch_Id(matchId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private SectionResponseDTO mapToResponse(Section section) {
        return SectionResponseDTO.builder()
                .id(section.getId())
                .nombre(section.getNombre())
                .capacidadTotal(section.getCapacidadTotal())
                .precio(section.getPrecio())
                .matchId(section.getMatch().getId())
                .build();
    }
}
