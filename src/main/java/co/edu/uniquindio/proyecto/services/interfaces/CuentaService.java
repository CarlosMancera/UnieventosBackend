package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.*;

import java.util.List;

public interface CuentaService {

    //TODO

    String crearCuenta(CrearCuentaDTO cuenta) throws Exception;

    String editarCuenta(EditarCuentaDTO cuenta) throws Exception;

    String eliminarCuenta(String id) throws Exception;

    InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception; //Al momento de entrar a su perfil

    String enviarCodigoRecuperacionPassword(String correo) throws Exception;   //Cuando olvida contraseña, y permite crear una nueva

    String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception;

    String iniciarSesion(LoginDTO loginDTO) throws Exception;

    public List<ItemCuentaDTO> listarCuentas() throws Exception;

}
