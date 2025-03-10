package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.CrearCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.EditarCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.InformacionCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.ItemCuentaDTO;
import co.edu.uniquindio.proyecto.model.entities.Cuenta;

import java.util.List;

public interface CuentaService {

    //TODO

    Long crearCuenta(CrearCuentaDTO cuenta) throws Exception;

    Long editarCuenta(EditarCuentaDTO cuenta) throws Exception;

    Long eliminarCuenta(Long id) throws Exception;

    InformacionCuentaDTO obtenerInformacionCuenta(Long id) throws Exception; //Al momento de entrar a su
    // perfil

    String enviarCodigoRecuperacionPassword(String correo) throws Exception;   //Cuando olvida contraseña, y permite crear una nueva

    String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception;

    TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception;


    public List<ItemCuentaDTO> listarCuentas() throws Exception;

    Cuenta enviaCuentaByCorreo(String correo) throws Exception;

    Cuenta actualizarCuenta(Cuenta cuenta) throws Exception;


}
