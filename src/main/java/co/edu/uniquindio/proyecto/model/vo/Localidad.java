package co.edu.uniquindio.proyecto.model.vo;

import lombok.*;
import org.bson.types.ObjectId;

//EMBEBIDAS EN EVENTOS
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Localidad {

    private String nombre;
    private double precio;
    private int capacidad;
    private int entradasVendidas;
    private boolean disponibilidad;
    private String descripcion;
    private String imagen;


}
