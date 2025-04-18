package co.edu.uniquindio.proyecto.dto.sectionDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SectionResponseDTO {
    private Long id;
    private String nombre;
    private int capacidadTotal;
    private BigDecimal precio;
    private Long matchId;
}
