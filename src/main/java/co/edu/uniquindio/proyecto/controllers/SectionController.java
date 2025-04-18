package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.sectionDTO.SectionDTO;
import co.edu.uniquindio.proyecto.dto.sectionDTO.SectionResponseDTO;
import co.edu.uniquindio.proyecto.services.interfaces.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/secciones")
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<Long> crear(@RequestBody SectionDTO dto) {
        return ResponseEntity.ok(sectionService.crearSection(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody SectionDTO dto) {
        sectionService.actualizarSection(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        sectionService.eliminarSection(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(sectionService.obtenerSection(id));
    }

    @GetMapping("/por-partido/{matchId}")
    public ResponseEntity<List<SectionResponseDTO>> listarPorMatch(@PathVariable Long matchId) {
        return ResponseEntity.ok(sectionService.listarSectionsPorMatch(matchId));
    }
}
