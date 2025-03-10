package co.edu.uniquindio.proyecto.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable // ✅ Importante para que funcione correctamente en JPA
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pago {

    @EqualsAndHashCode.Include
    private String codigoAutorizacion;

    private String metodo;
    private String estado;
    private double valorTransaccion;

    @Column(name = "fecha_pago") // ✅ Se renombra para evitar conflicto con `OrdenCompra.fecha`
    private LocalDateTime fechaPago;
}
