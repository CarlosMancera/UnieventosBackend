package co.edu.uniquindio.proyecto.model;

import co.edu.uniquindio.proyecto.enums.EstadoEvento;
import co.edu.uniquindio.proyecto.enums.TipoEvento;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("eventos")
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Evento {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private String direccion;
    private String ciudad;
    private String descripcion;
    private TipoEvento tipoEvento;
    private List<Imagen> imagenes; //O  private String imagenPortada y private String imagenLocalidades
    private LocalDateTime fecha;
    private List<Localidad> localidades;
    private EstadoEvento estadoEvento;




}
