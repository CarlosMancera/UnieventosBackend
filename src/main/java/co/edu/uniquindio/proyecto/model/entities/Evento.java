package co.edu.uniquindio.proyecto.model.entities;

import co.edu.uniquindio.proyecto.model.enums.EstadoEvento;
import co.edu.uniquindio.proyecto.model.enums.TipoEvento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String direccion;
    private String ciudad;

    @Column(length = 1000)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;

    private String imagen;

    private LocalDateTime fecha;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "evento_id")
    private List<Localidad> localidades;

    @Enumerated(EnumType.STRING)
    private EstadoEvento estadoEvento;

    private String imagenPortada;
    private String imagenLocalidades;

    @ManyToOne
    @JoinColumn(name = "artista_id")
    private Artista artista;

    // Métodos personalizados (no necesitan anotaciones adicionales en JPA)

    public int getCantidadLocalidades() {
        return localidades != null ? localidades.size() : 0;
    }

    public int getCantidadTotalEntradas() {
        return localidades.stream()
                .mapToInt(Localidad::getCapacidad)
                .sum();
    }

    public Localidad getLocalidadPorNombre(String nombreLocalidad) {
        return localidades.stream()
                .filter(localidad -> localidad.getNombre().equalsIgnoreCase(nombreLocalidad))
                .findFirst()
                .orElse(null);
    }

}
