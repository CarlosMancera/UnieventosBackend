package co.edu.uniquindio.proyecto.dto.ordenDTO;

import java.time.LocalDateTime;
import java.util.List;

public record OrdenCompraDTO(
        String id,
        LocalDateTime fechaCreacion,
        List<ResumenCarritoDTO> entradas,
        double subtotal, double descuento,
        double total
) {
}
