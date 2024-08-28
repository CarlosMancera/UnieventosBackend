package co.edu.uniquindio.proyecto.model;

import co.edu.uniquindio.proyecto.enums.EstadoCupon;
import co.edu.uniquindio.proyecto.enums.TipoCupon;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("cupones")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cupon {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String codigo;
    private String nombreDescriptivo;
    private double descuento;
    private LocalDateTime fecha_vencimiento;
    private TipoCupon tipoCupon;
    private EstadoCupon estado;
    private boolean es_especial;



}
