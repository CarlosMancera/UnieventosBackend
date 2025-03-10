package co.edu.uniquindio.proyecto.model.entities;

import co.edu.uniquindio.proyecto.model.vo.Pago;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ordenes_compra")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cuenta cliente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "orden_compra_id")
    private List<DetalleOrden> items;

    @ManyToOne
    @JoinColumn(name = "cupon_id")
    private Cupon cupon;

    private double subTotal;

    private double total;

    private LocalDateTime fecha;

    @Embedded
    private Pago pago;

    private String codigoPasarela;
}
