package co.edu.uniquindio.proyecto.model.docs;

import co.edu.uniquindio.proyecto.model.enums.EstadoCupon;
import co.edu.uniquindio.proyecto.model.enums.TipoCupon;
import lombok.*;
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
    private int limiteUso;

    @Builder
    public Cupon(String codigo, String nombreDescriptivo, double descuento, LocalDateTime fecha_vencimiento, TipoCupon tipoCupon, EstadoCupon estado, boolean es_especial, int limiteUso) {

        this.codigo = codigo;
        this.nombreDescriptivo = nombreDescriptivo;
        this.descuento = descuento;
        this.fecha_vencimiento = fecha_vencimiento;
        this.tipoCupon = tipoCupon;
        this.estado = estado;
        this.es_especial = es_especial;
    }
}
