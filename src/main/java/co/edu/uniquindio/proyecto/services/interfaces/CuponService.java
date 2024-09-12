package co.edu.uniquindio.proyecto.services.interfaces;
import java.util.List;

public interface CuponService {
    String crearCupon(CrearCuponDTO cuponDTO) throws Exception;
    String editarCupon(EditarCuponDTO cuponDTO) throws Exception;
    String eliminarCupon(String id) throws Exception;
    InformacionCuponDTO obtenerInformacionCupon(String id) throws Exception;
    List<ResumenCuponDTO> listarCupones();
    List<ResumenCuponDTO> buscarCuponesPorCodigo(String codigo);
    List<ResumenCuponDTO> buscarCuponesUtilizadosPorUsuario(String idUsuario);
}
