package co.edu.uniquindio.proyecto.dto.ordenDTO;

import org.bson.types.ObjectId;

public record AgregarAlCarritoDTO(
        ObjectId idCuenta,
        ObjectId idEvento,
        String localidad,
        int cantidad
) {
}
