package co.edu.uniquindio.proyecto.dto.Match;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResponseDTO {
    private Long id;
    private String equipoLocal;
    private String equipoVisitante;
    private LocalDateTime fechaHora;
    private String estado;
    private String tipoEvento;
    private String imagenPortada;
    private String descripcion;
}

