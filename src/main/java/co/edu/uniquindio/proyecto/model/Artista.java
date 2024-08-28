package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("artistas")
@Setter
@Getter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Artista {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private String tipo;
    private List<String> generos;
    private String biografia;
    private Contacto contacto;
    private double tarifa;
    private List<String> referencias;
    private Disponibilidad disponibilidad;

}
