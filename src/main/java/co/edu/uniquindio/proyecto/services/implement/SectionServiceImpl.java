package co.edu.uniquindio.proyecto.services.implement;


import co.edu.uniquindio.proyecto.dto.sectionDTO.SectionDTO;
import co.edu.uniquindio.proyecto.dto.sectionDTO.SectionResponseDTO;
import co.edu.uniquindio.proyecto.model.entities.Match;
import co.edu.uniquindio.proyecto.model.entities.Section;
import co.edu.uniquindio.proyecto.repositories.MatchRepository;
import co.edu.uniquindio.proyecto.repositories.SectionRepository;
import co.edu.uniquindio.proyecto.services.exception.CapacidadInvalidaException;
import co.edu.uniquindio.proyecto.services.interfaces.SectionService;
import jakarta.transaction.Transactional;
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
                .capacidadRestante(dto.getCapacidadTotal())
                .precio(dto.getPrecio())
                .match(match)
                .build();
        return sectionRepository.save(section).getId();
    }

    @Override
    @Transactional
    public void actualizarSection(Long id, SectionDTO dto) {
        Section section = sectionRepository.findById(id).orElseThrow();

        int boletasVendidas = section.getTickets() != null ? section.getTickets().size() : 0;

        if (dto.getCapacidadTotal() < boletasVendidas) {
            throw new CapacidadInvalidaException("No se puede establecer una capacidad total menor a las boletas ya vendidas (" + boletasVendidas + ").");
        }

        section.setNombre(dto.getNombre());

        int diferencia = dto.getCapacidadTotal() - section.getCapacidadTotal();

        section.setCapacidadTotal(dto.getCapacidadTotal());
        section.setPrecio(dto.getPrecio());

        if (diferencia != 0) {
            section.setCapacidadRestante(section.getCapacidadRestante() + diferencia);
        }

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
                .capacidadRestante(section.getCapacidadRestante())
                .precio(section.getPrecio())
                .matchId(section.getMatch().getId())
                .build();
    }
}
