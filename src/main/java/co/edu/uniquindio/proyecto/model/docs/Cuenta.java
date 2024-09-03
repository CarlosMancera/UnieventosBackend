package co.edu.uniquindio.proyecto.model.docs;

import co.edu.uniquindio.proyecto.model.vo.CodigoValidacion;
import co.edu.uniquindio.proyecto.model.enums.EstadoCuenta;
import co.edu.uniquindio.proyecto.model.enums.TipoUsuario;
import co.edu.uniquindio.proyecto.model.vo.Usuario;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

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
    private Usuario usuario;
    private LocalDateTime fechaRegistro;
    private String password;
    private CodigoValidacion codigoValidacionRegistro;
    private List<LocalDateTime> historialConexion;

    @Builder
    public Cuenta(String email, TipoUsuario tipoUsuario, EstadoCuenta estadoCuenta, CodigoValidacion codigoValidacionPassword, Usuario usuario, LocalDateTime fechaRegistro, String password, CodigoValidacion codigoValidacionRegistro) {

        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.estadoCuenta = estadoCuenta;
        this.codigoValidacionPassword = codigoValidacionPassword;
        this.usuario = usuario;
        this.fechaRegistro = fechaRegistro;
        this.password = password;
        this.codigoValidacionRegistro = codigoValidacionRegistro;
    }


}
