package co.edu.uniquindio.proyecto.model.vo;

import lombok.*;
import org.bson.types.ObjectId;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleOrden {

    private String nombreLocalidad;
    private int cantidad;
    private ObjectId idEvento;
    private double precioUnitario;

    @Builder
    public DetalleOrden(String nombreLocalidad, int cantidad, ObjectId idEvento, double precioUnitario) {
        this.nombreLocalidad = nombreLocalidad;
        this.cantidad = cantidad;
        this.idEvento = idEvento;
        this.precioUnitario = precioUnitario;
    }
}
