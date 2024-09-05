package co.edu.uniquindio.proyecto.model.vo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {


    private String cedula;
    private String nombreCompleto;
    private String direccion;
    private String telefono;

}
