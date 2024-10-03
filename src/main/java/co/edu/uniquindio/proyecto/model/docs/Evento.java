package co.edu.uniquindio.proyecto.model.docs;

import co.edu.uniquindio.proyecto.model.vo.Imagen;
import co.edu.uniquindio.proyecto.model.enums.EstadoEvento;
import co.edu.uniquindio.proyecto.model.enums.TipoEvento;
import co.edu.uniquindio.proyecto.model.vo.Localidad;
import lombok.*;
import org.bson.types.ObjectId;
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
    private String imagen; //O  private String imagenPortada y private String imagenLocalidades
    private LocalDateTime fecha;
    private List<Localidad> localidades;
    private EstadoEvento estadoEvento;
    private ObjectId artista;
    private EstadoEvento estado;

    @Builder
    public Evento(String nombre, String direccion, String ciudad, String descripcion, TipoEvento tipoEvento, String imagenes, LocalDateTime fecha, List<Localidad> localidades, EstadoEvento estadoEvento, ObjectId artista) {

        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.tipoEvento = tipoEvento;
        this.imagen = imagenes;
        this.fecha = fecha;
        this.localidades = localidades;
        this.estadoEvento = estadoEvento;
        this.artista = artista;
    }

    public int getCapacidad(){

        int capacidad = 0;

        for(Localidad localidad : localidades){

            capacidad+=localidad.getCapacidad();

        }

        return capacidad;

    }
}
