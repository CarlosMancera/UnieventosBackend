package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.CambiarPasswordDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.CrearCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.EditarCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.InformacionCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.ItemCuentaDTO;
import co.edu.uniquindio.proyecto.services.interfaces.CuentaService;
import com.sanctionco.jmail.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cuenta")
public class CuentaController {

    private final CuentaService cuentaService;

    public void crearCuenta(CrearCuentaDTO cuenta) throws Exception{
    }


    public void editarCuenta(EditarCuentaDTO cuenta) throws Exception{
    }


    public void eliminarCuenta(String id) throws Exception{
    }


    public InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception{
        return null;
    }


    public List<ItemCuentaDTO> listarCuentas() throws Exception{
        return null;
    }

    public  enviarCodigoRecuperacionPassword(String correo) throws Exception; {

        return null;
    }  //Cuando olvida contraseña, y permite crear una nueva

    public void cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception{

    }

    TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception{
        return null;
    }




}
