package co.edu.uniquindio.proyecto.dto.sectionDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SectionDTO {
    private Long matchId;
    private String nombre;
    private int capacidadTotal;
    private BigDecimal precio;
}
