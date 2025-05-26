package co.edu.uniquindio.proyecto.model.entities;

import co.edu.uniquindio.proyecto.model.enums.EstadoCuenta;
import co.edu.uniquindio.proyecto.model.enums.TipoUsuario;
import co.edu.uniquindio.proyecto.model.vo.CodigoValidacion;
import co.edu.uniquindio.proyecto.model.vo.Usuario;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cuentas")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "codigo", column = @Column(name = "codigo_password")),
            @AttributeOverride(name = "fechaCreacion", column = @Column(name = "fecha_creacion_password")),
            @AttributeOverride(name = "fechaExpiracion", column = @Column(name = "fecha_expiracion_password"))
    })
    private CodigoValidacion codigoValidacionPassword;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "codigo", column = @Column(name = "codigo_registro")),
            @AttributeOverride(name = "fechaCreacion", column = @Column(name = "fecha_creacion_registro"))
    })
    private CodigoValidacion codigoValidacionRegistro;

    @Embedded
    private Usuario usuario;

    private LocalDateTime fechaRegistro;

    private String password;

    @Column(name = "token", columnDefinition = "text")
    private String token;

    @Column(name = "en_sesion", nullable = false, columnDefinition = "boolean default false")
    private Boolean enSesion = false;

    @OneToMany(mappedBy = "comprador")
    private List<Ticket> tickets;

}
