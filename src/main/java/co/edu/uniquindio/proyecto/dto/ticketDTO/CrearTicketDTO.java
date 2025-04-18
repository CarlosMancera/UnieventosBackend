package co.edu.uniquindio.proyecto.dto.ticketDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CrearTicketDTO {

    private Long matchId;
    private Long sectionId;
    private Long cuentaId;

    private String cedulaPortador;
    private String nombrePortador;
    private String direccionPortador;
    private String telefonoPortador;
    private String emailPortador;
}
