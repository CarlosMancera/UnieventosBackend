package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.FiltroEventoDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.InformacionEventoDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.ItemEventoDTO;
import co.edu.uniquindio.proyecto.dto.eventoDTO.ResumenEventoDTO;
import co.edu.uniquindio.proyecto.services.interfaces.EventoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/publico")
public class PublicoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/obtener-info-evento/{id}")
    public ResponseEntity<MensajeDTO<InformacionEventoDTO>> obtenerInformacionEvento(@PathVariable String id) throws Exception {
        InformacionEventoDTO info = eventoService.obtenerInformacionEvento(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, info));
    }

    @GetMapping("/listar-eventos")
    public ResponseEntity<MensajeDTO<List<ResumenEventoDTO>>> listarEventos() throws Exception {
        List<ResumenEventoDTO> lista = eventoService.listarEventos();
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    @PostMapping("/filtrar-eventos")
    public ResponseEntity<MensajeDTO<List<ItemEventoDTO>>> filtrarEventos(@Valid @RequestBody FiltroEventoDTO filtroDTO) throws Exception {
        List<ItemEventoDTO> lista = eventoService.filtrarEventos(filtroDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }

    @GetMapping("/buscar-evento/{nombre}")
    public ResponseEntity<MensajeDTO<List<ResumenEventoDTO>>> buscarEventosPorNombre(@PathVariable String nombre) {
        List<ResumenEventoDTO> lista = eventoService.buscarEventosPorNombre(nombre);
        return ResponseEntity.ok(new MensajeDTO<>(false, lista));
    }


}
