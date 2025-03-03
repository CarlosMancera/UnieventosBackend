package co.edu.uniquindio.proyecto.model.docs;

import co.edu.uniquindio.proyecto.model.vo.DetalleCarrito;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("carritos")
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Carrito {

    @EqualsAndHashCode.Include
    @Id
    private String id;

    private LocalDateTime fecha;
    private List<DetalleCarrito> items;
    private ObjectId idUsuario;
    private double total;
    private double subTotal;
    private String cupon;

    @Builder
    public Carrito(LocalDateTime fecha, List<DetalleCarrito> items, ObjectId idUsuario, double total, double subTotal, String cupon) {
        this.fecha = fecha;
        this.items = items;
        this.idUsuario = idUsuario;
        this.total = total;
        this.subTotal = subTotal;
        this.cupon = cupon;
    }
}

