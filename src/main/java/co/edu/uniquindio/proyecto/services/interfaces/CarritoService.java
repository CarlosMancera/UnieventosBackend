package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.ordenDTO.AgregarAlCarritoDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.ResumenCarritoDTO;

import java.util.List;

public interface CarritoService {

    Long agregarAlCarrito(AgregarAlCarritoDTO agregarAlCarritoDTO) throws Exception;

    void eliminarDelCarrito(Long idCuenta, Long idEntrada) throws Exception;

    List<ResumenCarritoDTO> listarCarrito(Long idCuenta) throws Exception;
}
