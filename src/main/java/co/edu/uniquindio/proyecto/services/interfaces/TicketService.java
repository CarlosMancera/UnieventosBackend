package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.compraDTO.CrearCompraDTO;
import co.edu.uniquindio.proyecto.dto.ticketDTO.CrearTicketDTO;
import co.edu.uniquindio.proyecto.dto.ticketDTO.ItemTicketDTO;

import java.util.List;

public interface TicketService {
    Long crearTicket(CrearTicketDTO dto) throws Exception;
    List<ItemTicketDTO> listarTicketsPorCuenta(Long cuentaId);
    void crearCompra(CrearCompraDTO dto) throws Exception;

}

