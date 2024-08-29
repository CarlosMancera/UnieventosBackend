package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.EditarCuentaDTO;
import co.edu.uniquindio.proyecto.dto.InformacionCuentaDTO;
import co.edu.uniquindio.proyecto.dto.RegistroCuentaDTO;
import co.edu.uniquindio.proyecto.model.docs.Cuenta;

public interface CuentaService {

    //TODO

    String crearCuenta(RegistroCuentaDTO cuenta) throws Exception;

    String editarCuenta(EditarCuentaDTO cuenta) throws Exception;

    String eliminarCuenta(String id) throws Exception;

    InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception;

}
