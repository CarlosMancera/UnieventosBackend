package co.edu.uniquindio.proyecto.model.vo;

import lombok.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Disponibilidad {

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
