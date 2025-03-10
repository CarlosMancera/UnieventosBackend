package co.edu.uniquindio.proyecto.model.vo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalles_carrito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DetalleCarrito {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  // 🔹 Clave primaria obligatoria para una entidad

	private String nombreLocalidad;
	private int cantidad;
	private Long idEvento;
	private double precioUnitario;
}
