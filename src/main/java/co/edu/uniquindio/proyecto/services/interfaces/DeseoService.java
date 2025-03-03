package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.deseoDTO.AgregarDeseoDTO;
import co.edu.uniquindio.proyecto.dto.deseoDTO.ResumenDeseoDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface DeseoService {
    String agregarDeseo(AgregarDeseoDTO deseoDTO) throws Exception;
    void eliminarDeseo(ObjectId idCuenta, ObjectId idEvento) throws Exception;
    List<ResumenDeseoDTO> listarDeseos(ObjectId idCuenta) throws Exception;
    List<ResumenDeseoDTO> buscarDeseos(ObjectId idCuenta, String nombreEvento) throws Exception;
    void enviarNotificaciones();
}
