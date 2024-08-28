package co.edu.uniquindio.proyecto.model.vo;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CodigoValidacion {

    private String codigo;
    private LocalDateTime fechaCreacion;

}
