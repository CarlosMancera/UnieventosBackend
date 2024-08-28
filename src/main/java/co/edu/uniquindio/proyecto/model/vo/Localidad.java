package co.edu.uniquindio.proyecto.model.vo;

import lombok.*;
import org.bson.types.ObjectId;

//EMBEBIDAS EN EVENTOS
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Localidad {

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
