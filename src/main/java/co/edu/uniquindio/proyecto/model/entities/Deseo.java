package co.edu.uniquindio.proyecto.model.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "deseos")
@Builder
public class Deseo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    private boolean recibeInfo;

}
