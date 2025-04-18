package co.edu.uniquindio.proyecto.controllers;


import co.edu.uniquindio.proyecto.dto.teamDTO.TeamDTO;
import co.edu.uniquindio.proyecto.dto.teamDTO.TeamResponseDTO;
import co.edu.uniquindio.proyecto.services.interfaces.TeamService;
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
@RequestMapping("/equipos")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<Long> crear(@RequestBody TeamDTO dto) {
        return ResponseEntity.ok(teamService.crearEquipo(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.obtenerEquipo(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(@PathVariable Long id, @RequestBody TeamDTO dto) {
        teamService.actualizarEquipo(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        teamService.eliminarEquipo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> listar() {
        return ResponseEntity.ok(teamService.listarEquipos());
    }
}

