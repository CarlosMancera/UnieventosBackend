package co.edu.uniquindio.proyecto.model.vo;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    private String cedula;
    private String nombre;
    private String direccion;
    private String telefono;
}
