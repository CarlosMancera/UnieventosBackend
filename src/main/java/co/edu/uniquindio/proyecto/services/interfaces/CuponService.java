package co.edu.uniquindio.proyecto.services.interfaces;
import co.edu.uniquindio.proyecto.dto.cuponDTO.CrearCuponDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.EditarCuponDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.InformacionCuponDTO;
import co.edu.uniquindio.proyecto.dto.cuponDTO.ResumenCuponDTO;

import java.util.List;

public interface CuponService {
    Long crearCupon(CrearCuponDTO cuponDTO) throws Exception;
    Long editarCupon(EditarCuponDTO cuponDTO) throws Exception;
    Long eliminarCupon(Long id) throws Exception;
    InformacionCuponDTO obtenerInformacionCupon(Long id) throws Exception;
    List<ResumenCuponDTO> listarCupones();
    List<ResumenCuponDTO> buscarCuponesPorCodigo(String codigo);

}
