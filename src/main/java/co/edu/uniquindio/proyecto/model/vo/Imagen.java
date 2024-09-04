package co.edu.uniquindio.proyecto.model.vo;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Imagen {

    @EqualsAndHashCode.Include
    private String url;

    private String nombre;
    private String descripcion;

}
