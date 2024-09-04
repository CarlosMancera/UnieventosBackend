package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.*;
import co.edu.uniquindio.proyecto.repositories.CuentaRepo;
import co.edu.uniquindio.proyecto.services.interfaces.CuentaService;

public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepo cuentaRepo;

    public CuentaServiceImpl(CuentaRepo cuentaRepo) {

        this.cuentaRepo = cuentaRepo;

    }
    @Override
    public String crearCuenta(CrearCuentaDTO cuenta) throws Exception {
        return "";
    }

    @Override
    public String editarCuenta(EditarCuentaDTO cuenta) throws Exception {
        return "";
    }

    @Override
    public String eliminarCuenta(String id) throws Exception {
        return "";
    }

    @Override
    public InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception {
        return null;
    }

    @Override
    public String enviarCodigoRecuperacionPassword(String correo) throws Exception {
        return "";
    }

    @Override
    public String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception {
        return "";
    }

    @Override
    public String iniciarSesion(LoginDTO loginDTO) throws Exception {
        return "";
    }
}
