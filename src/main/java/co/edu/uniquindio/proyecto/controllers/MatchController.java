package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.Match.MatchDTO;
import co.edu.uniquindio.proyecto.dto.Match.MatchResponseDTO;
import co.edu.uniquindio.proyecto.services.interfaces.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/partidos")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<Long> crear(@RequestBody MatchDTO dto) {
        return ResponseEntity.ok(matchService.crearPartido(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.obtenerPartido(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody MatchDTO dto) {
        matchService.actualizarPartido(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        matchService.eliminarPartido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<MatchResponseDTO>> listar() {
        return ResponseEntity.ok(matchService.listarPartidos());
    }
}

