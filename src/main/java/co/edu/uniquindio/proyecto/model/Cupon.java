package co.edu.uniquindio.proyecto.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cupon {

    private String id;
    private String codigo;
    private String nombreDescriptivo;
    private double descuento;
    private LocalDateTime fecha_vencimiento;
    private EnumTipoCupon tipoCupon;
    private EnumEstadoCupon estado;
    private boolean es_especial;

    //Quitar usuarios, es_especial, cupones



}
