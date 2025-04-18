package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.ticketDTO.CrearTicketDTO;
import co.edu.uniquindio.proyecto.services.interfaces.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<Long> crearTicket(@RequestBody CrearTicketDTO dto) throws Exception {
        return ResponseEntity.ok(ticketService.crearTicket(dto));
    }
}
