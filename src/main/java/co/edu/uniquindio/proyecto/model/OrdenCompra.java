package co.edu.uniquindio.proyecto.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("ordenes_compra")
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class OrdenCompra {

    @EqualsAndHashCode.Include
    @Id
    private String id;

    private String codigo;
    private ObjectId cliente;
    private List<DetalleOrden> items;
    private ObjectId cupon;
    private double total;
    private LocalDateTime fecha;
    private Pago pago;



}
