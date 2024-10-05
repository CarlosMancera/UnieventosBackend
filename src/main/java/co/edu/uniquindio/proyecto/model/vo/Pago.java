package co.edu.uniquindio.proyecto.model.vo;

import co.edu.uniquindio.proyecto.model.enums.string;
import lombok.*;
import java.time.LocalDateTime;

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
    private LocalDateTime fecha;
}
