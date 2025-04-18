package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.ticketDTO.CrearTicketDTO;

public interface TicketService {
    Long crearTicket(CrearTicketDTO dto) throws Exception;
}

