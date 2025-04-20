package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.ActualizarPasswordDTO;
import co.edu.uniquindio.proyecto.dto.LoginDTO;
import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.TokenDTO;
import co.edu.uniquindio.proyecto.services.interfaces.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AutenticacionController {


    private final CuentaService cuentaService;

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<MensajeDTO<TokenDTO>> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        var cuenta = cuentaService.enviaCuentaByCorreo(loginDTO.email());

        if (Boolean.TRUE.equals(cuenta.getEnSesion())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MensajeDTO<>(true, null));
        }

        TokenDTO token = cuentaService.iniciarSesion(loginDTO);
        cuenta.setToken(token.token()); 
        cuenta.setEnSesion(true);
        cuentaService.actualizarCuenta(cuenta);

        return ResponseEntity.ok(new MensajeDTO<>(false, token));
    }



    @PostMapping(value = "/close-sesion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeDTO<TokenDTO>> cerrarSesion(@Valid @RequestBody String email) throws Exception {
        var cuenta = cuentaService.enviaCuentaByCorreo(email);
        cuenta.setToken("");
        cuenta.setEnSesion(false);
        cuentaService.actualizarCuenta(cuenta);
        // Se retorna un TokenDTO con un mensaje indicando que se cerró la sesión correctamente.
        return ResponseEntity.ok(new MensajeDTO<>(false, new TokenDTO("Cerró sesión correctamente")));
    }

    @PostMapping(value = "/actualizar-password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MensajeDTO<Boolean>> actualizarPassword(@Valid @RequestBody ActualizarPasswordDTO dto) throws Exception {
        // Se obtiene la cuenta según el correo proporcionado.
        var cuenta = cuentaService.enviaCuentaByCorreo(dto.email());
        // Si la cuenta no existe o no tiene código de validación, se retorna error.
        if (cuenta == null || cuenta.getCodigoValidacionPassword() == null) {
            return ResponseEntity.ok(new MensajeDTO<>(true, false));
        }
        // Verificar que el código ingresado coincida con el código almacenado.
        if (!cuenta.getCodigoValidacionPassword().getCodigo().equals(dto.codigo())) {
            return ResponseEntity.ok(new MensajeDTO<>(true, false));
        }
        if (cuenta.getCodigoValidacionPassword().getFechaExpiracion().isBefore(LocalDateTime.now())) {
            return ResponseEntity.ok(new MensajeDTO<>(true, false));
        }
        cuenta.setPassword(encriptarPassword(dto.nuevaPassword()));
        cuenta.setCodigoValidacionPassword(null);
        cuentaService.actualizarCuenta(cuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, true));
    }

    public String encriptarPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode( password );
    }
}