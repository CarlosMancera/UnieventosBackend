package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.sectionDTO.SectionDTO;
import co.edu.uniquindio.proyecto.dto.sectionDTO.SectionResponseDTO;

import java.util.List;

public interface SectionService {
    Long crearSection(SectionDTO dto);
    void actualizarSection(Long id, SectionDTO dto);
    void eliminarSection(Long id);
    SectionResponseDTO obtenerSection(Long id);
    List<SectionResponseDTO> listarSectionsPorMatch(Long matchId);
}
