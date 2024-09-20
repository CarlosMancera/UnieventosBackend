package co.edu.uniquindio.proyecto.model.docs;

import co.edu.uniquindio.proyecto.model.enums.EstadoArtista;
import co.edu.uniquindio.proyecto.model.vo.Contacto;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("artistas")
@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Artista {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String nombre;
    private String tipo;
    private List<String> generos;
    private String biografia;
    private Contacto contacto;
    private double tarifa;
    private List<String> referencias;


    @Builder
    public Artista(String nombre, String tipo, List<String> generos, String biografia, Contacto contacto, double tarifa, List<String> referencias, EstadoArtista estado) {

    }



}
