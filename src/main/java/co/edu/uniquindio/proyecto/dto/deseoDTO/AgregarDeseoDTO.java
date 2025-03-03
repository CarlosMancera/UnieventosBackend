package co.edu.uniquindio.proyecto.dto.deseoDTO;

import org.bson.types.ObjectId;

public record AgregarDeseoDTO(
        ObjectId idCuenta,
        ObjectId idEvento,
        boolean recibeInfo
) {
}
