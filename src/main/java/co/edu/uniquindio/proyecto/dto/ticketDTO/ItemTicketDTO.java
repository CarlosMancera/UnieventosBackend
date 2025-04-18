package co.edu.uniquindio.proyecto.dto.ticketDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemTicketDTO {
    private Long id;
    private String codigo;
    private String nombrePortador;
    private String emailPortador;
    private String nombreMatch;
    private String nombreSeccion;
    private LocalDateTime fechaCompra;
    private String estado;
}

