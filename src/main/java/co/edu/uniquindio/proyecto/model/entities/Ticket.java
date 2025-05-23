package co.edu.uniquindio.proyecto.model.entities;

import co.edu.uniquindio.proyecto.model.vo.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @ManyToOne
    private Match match;

    @ManyToOne
    private Section section;

    @ManyToOne
    private Cuenta comprador;

    @Embedded
    private Usuario portador;

    @Enumerated(EnumType.STRING)
    private EstadoTicket estado;

    private LocalDateTime fechaCompra;

    @Column(nullable = false)
    private String emailPortador;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL)
    private Payment pago;

    public enum EstadoTicket {
        COMPRADA, USADA, CANCELADA
    }
}
