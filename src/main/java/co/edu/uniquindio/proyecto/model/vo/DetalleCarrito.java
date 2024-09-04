package co.edu.uniquindio.proyecto.model.vo;

import lombok.*;
import org.bson.types.ObjectId;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class DetalleCarrito {

    private String nombreLocalidad;
    private int cantidad;
    private ObjectId idEvento;

}
