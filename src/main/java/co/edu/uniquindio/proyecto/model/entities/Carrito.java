package co.edu.uniquindio.proyecto.model.entities;

import co.edu.uniquindio.proyecto.model.vo.DetalleCarrito;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "carritos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carrito_id")
    private List<DetalleCarrito> items;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false) // 🔹 Relación con Cuenta
    private Cuenta cuenta;

    private double total;
    private double subTotal;
    private String cupon;
}
