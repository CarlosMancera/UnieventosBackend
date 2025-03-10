package co.edu.uniquindio.proyecto.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "detalles_orden")
public class DetalleOrden {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombreLocalidad;

	private int cantidad;

	private double precioUnitario;

	@ManyToOne
	@JoinColumn(name = "evento_id", nullable = false)
	private Evento evento;

	@ManyToOne
	@JoinColumn(name = "orden_compra_id", nullable = false)
	private OrdenCompra ordenCompra;
}
