package co.edu.uniquindio.proyecto.dto.ordenDTO;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public record ResumenCarritoDTO(
        ObjectId id,
        String nombreEvento,
        LocalDateTime fechaEvento,
        String localidad, int cantidad,
        double precioUnitario
) {
}
