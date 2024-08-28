package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.bson.types.ObjectId;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleOrden {

    @EqualsAndHashCode.Include
    private String id;

    private ObjectId evento;
    private String localidad;
    private int cantidad;
    private double precioUnitario;

}
