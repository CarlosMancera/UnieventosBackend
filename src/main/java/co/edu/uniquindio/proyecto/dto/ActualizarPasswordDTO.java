package co.edu.uniquindio.proyecto.dto;

import jakarta.validation.constraints.NotBlank;

public record ActualizarPasswordDTO(
		@NotBlank String email,
		@NotBlank String codigo,
		@NotBlank String nuevaPassword
) { }
