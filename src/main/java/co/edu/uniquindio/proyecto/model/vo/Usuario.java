package co.edu.uniquindio.proyecto.model.vo;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable // ✅ Debe ser @Embeddable para ser usada dentro de otra entidad
@Getter
@Setter
@NoArgsConstructor // ✅ Constructor vacío necesario para JPA
@AllArgsConstructor // ✅ Constructor con argumentos para crear objetos fácilmente
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    private String cedula;
    private String nombre;
    private String direccion;
    private String telefono;
}
