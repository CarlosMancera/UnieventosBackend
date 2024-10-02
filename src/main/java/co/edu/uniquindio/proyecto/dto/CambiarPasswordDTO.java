package co.edu.uniquindio.proyecto.dto;

public record CambiarPasswordDTO(
        String correo,
        String codigoVerificacion,
        String passwordNuevaT
) {
}
