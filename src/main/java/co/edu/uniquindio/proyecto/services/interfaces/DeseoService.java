package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.deseoDTO.AgregarDeseoDTO;
import co.edu.uniquindio.proyecto.dto.deseoDTO.ResumenDeseoDTO;

import java.util.List;

public interface DeseoService {

    String agregarDeseo(AgregarDeseoDTO deseoDTO) throws Exception;

    void eliminarDeseo(Long idCuenta, Long idEvento) throws Exception;

    List<ResumenDeseoDTO> listarDeseos(Long idCuenta) throws Exception;

    List<ResumenDeseoDTO> buscarDeseos(Long idCuenta, String nombreEvento) throws Exception;

    void enviarNotificaciones();
}
