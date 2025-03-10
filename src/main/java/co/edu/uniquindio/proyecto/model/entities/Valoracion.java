package co.edu.uniquindio.proyecto.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "valoraciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Cuenta usuario;

    private int puntuacion;

    @Column(length = 1000)
    private String comentario;

    private LocalDateTime fecha;

}
