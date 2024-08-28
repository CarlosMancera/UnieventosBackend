package co.edu.uniquindio.proyecto.model;

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
