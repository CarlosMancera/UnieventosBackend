package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.CambiarPasswordDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.CrearCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.EditarCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.InformacionCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.ItemCuentaDTO;
import co.edu.uniquindio.proyecto.dto.emailDTO.EmailDTO;
import co.edu.uniquindio.proyecto.services.interfaces.CuentaService;
import co.edu.uniquindio.proyecto.services.interfaces.EmailService;
import com.sanctionco.jmail.Email;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cuenta")
public class CuentaController {

    @Autowired
    private final CuentaService cuentaService;
    @PostMapping("/crear-cuenta")
    public void crearCuenta(@Valid @RequestBody CrearCuentaDTO cuenta) throws Exception{
        cuentaService.crearCuenta(cuenta);
    }

    @PutMapping("/editar-perfil")
    public void editarCuenta(@Valid @RequestBody EditarCuentaDTO cuenta) throws Exception{
        cuentaService.editarCuenta(cuenta);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarCuenta(@PathVariable String id) throws Exception{
        cuentaService.eliminarCuenta(id);
    }

    @GetMapping("/obtener/{id}")
    public InformacionCuentaDTO obtenerInformacionCuenta(@PathVariable String id) throws Exception{
        return cuentaService.obtenerInformacionCuenta(id);
    }

    @GetMapping("/listar-todo")
    public List<ItemCuentaDTO> listarCuentas() throws Exception{
        return cuentaService.listarCuentas();
    }
    @PostMapping("/recuperar-password")
    public enviarCodigoRecuperacionPassword(@RequestParam String email) throws Exception; {

        try{
            EmailService.recuparContrasenna(email);
            return ResponseEntity.ok().body(
                    new MensajeDTO<>(false, "Link enviado"));
        }
        catch (Exception ex) {
            return ResponseEntity.ok().body(
                    new MensajeDTO<>(true, ex.getMessage()));
        }

    }  //Cuando olvida contraseña, y permite crear una nueva
    @PutMapping("/cambiar-password")
    public void cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception{

    }

    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception{
        return null;
    }




}
