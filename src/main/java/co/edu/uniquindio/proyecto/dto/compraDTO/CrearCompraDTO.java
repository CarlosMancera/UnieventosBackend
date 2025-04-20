package co.edu.uniquindio.proyecto.dto.compraDTO;

import co.edu.uniquindio.proyecto.dto.ticketDTO.CrearTicketDTO;
import lombok.Data;

import java.util.List;

@Data
public class CrearCompraDTO {
    private Long cuentaId;
    private Long matchId;
    private List<CrearTicketDTO> tickets;
}
