package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("localidades")
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Localidad {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private ObjectId evento;
    private double precio;
    private int capacidad;
    private int vendido;
    private int disponibilidad;
    private String descripcion;
    private String imagen;
}
