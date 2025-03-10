package co.edu.uniquindio.proyecto.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "localidades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Localidad {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;  // 🔹 Clave primaria obligatoria para una entidad

	private String nombre;
	private double precio;
	private int capacidad;
	private int entradasVendidas;
	private boolean disponibilidad;
	private String descripcion;
	private String imagen;
}
