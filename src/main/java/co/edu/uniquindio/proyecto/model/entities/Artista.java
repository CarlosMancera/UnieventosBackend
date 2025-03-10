package co.edu.uniquindio.proyecto.model.entities;

import co.edu.uniquindio.proyecto.model.enums.EstadoArtista;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "artistas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String genero;

    private String email;

    private String telefono;

    @Enumerated(EnumType.STRING)
    private EstadoArtista estado;
}
