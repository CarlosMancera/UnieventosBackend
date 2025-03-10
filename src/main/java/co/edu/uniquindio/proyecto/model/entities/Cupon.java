package co.edu.uniquindio.proyecto.model.entities;

import co.edu.uniquindio.proyecto.model.enums.EstadoCupon;
import co.edu.uniquindio.proyecto.model.enums.TipoCupon;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cupones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigo;

    private double descuento;

    private LocalDateTime fechaVencimiento;

    @Enumerated(EnumType.STRING)
    private TipoCupon tipoCupon;

    @Enumerated(EnumType.STRING)
    private EstadoCupon estado;

    private boolean esEspecial;

    private int limiteUso;

    private int cantidadUsados;
}
