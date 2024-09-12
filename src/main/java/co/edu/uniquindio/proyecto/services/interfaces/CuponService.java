package co.edu.uniquindio.proyecto.services.interfaces;
import co.edu.uniquindio.proyecto.dto.cuponDTO.CrearCuponDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.EditarCuponDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.InformacionCuponDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.ResumenCuponDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CuponService {
    String crearCupon(CrearCuponDTO cuponDTO) throws Exception;
    String editarCupon(EditarCuponDTO cuponDTO) throws Exception;
    String eliminarCupon(String id) throws Exception;
    InformacionCuponDTO obtenerInformacionCupon(String id) throws Exception;
    List<ResumenCuponDTO> listarCupones();
    List<ResumenCuponDTO> buscarCuponesPorCodigo(String codigo);
    //List<ResumenCuponDTO> buscarCuponesUtilizadosPorUsuario(String idUsuario);
}
