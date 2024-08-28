package co.edu.uniquindio.proyecto.model;

import co.edu.uniquindio.proyecto.enums.EstadoCuenta;
import co.edu.uniquindio.proyecto.enums.TipoUsuario;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("cuentas")
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Cuenta {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String email;
    private TipoUsuario tipoUsuario;
    private EstadoCuenta estadoCuenta;
    private CodigoValidacion codigoValidacionPassword;
    private ObjectId idUsuario;
    private LocalDateTime fechaRegistro;
    private String password;
    private CodigoValidacion codigoValidacionRegistro;
}
