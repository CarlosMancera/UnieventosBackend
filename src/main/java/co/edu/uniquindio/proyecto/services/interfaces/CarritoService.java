package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.ordenDTO.AgregarAlCarritoDTO;
import co.edu.uniquindio.proyecto.dto.ordenDTO.ResumenCarritoDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface CarritoService {

    String agregarAlCarrito(AgregarAlCarritoDTO agregarAlCarritoDTO) throws Exception;

    public void eliminarDelCarrito(ObjectId idCuenta, ObjectId idEntrada) throws Exception;

    public List<ResumenCarritoDTO> listarCarrito(ObjectId idCuenta) throws Exception;
}
