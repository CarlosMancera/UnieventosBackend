package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.CambiarPasswordDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.CrearCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.EditarCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.InformacionCuentaDTO;
import co.edu.uniquindio.proyecto.dto.cuentaDTO.ItemCuentaDTO;
import co.edu.uniquindio.proyecto.services.interfaces.CuentaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/editar-perfil")
    public void editarCuenta(@Valid @RequestBody EditarCuentaDTO cuenta) throws Exception{
        cuentaService.editarCuenta(cuenta);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/eliminar-cuenta/{id}")
    public void eliminarCuenta(@PathVariable Long id) throws Exception{
        cuentaService.eliminarCuenta(id);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/obtener-info-cuenta/{id}")
    public InformacionCuentaDTO obtenerInformacionCuenta(@PathVariable Long id) throws Exception{
        return cuentaService.obtenerInformacionCuenta(id);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/recuperar-password")
    public ResponseEntity<MensajeDTO<String>> enviarCodigoRecuperacionPassword(@RequestParam String correo) throws Exception {
        String mensaje = cuentaService.enviarCodigoRecuperacionPassword(correo);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    //Cuando olvida contraseña, y permite crear una eva
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/cambiar-password")
    public ResponseEntity<MensajeDTO<String>> cambiarPassword(@Valid @RequestBody CambiarPasswordDTO cambiarPasswordDTO) throws Exception {
        String mensaje = cuentaService.cambiarPassword(cambiarPasswordDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/iniciar-sesion")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        TokenDTO token = cuentaService.iniciarSesion(loginDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, token));
    }



}
