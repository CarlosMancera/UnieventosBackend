package co.edu.uniquindio.proyecto.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Artista {

    private String id;
    private String nombre;
    private String tipo;
    private List<String> generos;
    private String biografia;
    private Contacto contacto;
    private double tarifa;
    private List<String> referencias;

}
