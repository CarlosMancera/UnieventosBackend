package co.edu.uniquindio.proyecto.model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String equipoLocal;
    private String equipoVisitante;
    private LocalDateTime fechaHora;
    @Enumerated(EnumType.STRING)
    private EstadoMatch estado;
    private String tipoEvento;
    private String imagenPortada;
    private String descripcion;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private List<Section> localidades;

    public enum EstadoMatch {
        PROGRAMADO, EN_JUEGO, FINALIZADO, CANCELADO
    }
}