package co.edu.uniquindio.proyecto.controllers;

import co.edu.uniquindio.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyecto.dto.emailDTO.EmailDTO;
import co.edu.uniquindio.proyecto.services.interfaces.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping(value = "/enviar-email")
    public ResponseEntity<MensajeDTO<String>> enviarEmail(@Valid @RequestBody EmailDTO emailDTO) throws Exception {
        try{
            emailService.enviarEmail(emailDTO);
            return ResponseEntity.ok().body(
                    new MensajeDTO<>(false, "email enviado"));
        }
        catch (Exception ex) {
            return ResponseEntity.ok().body(
                    new MensajeDTO<>(true, ex.getMessage()));
        }
    }

    @PostMapping("/codigo-pago")
    public ResponseEntity<Void> enviarCodigo(@RequestBody EmailDTO emailDTO) throws Exception {
        emailService.enviarEmail(emailDTO);
        return ResponseEntity.ok().build();
    }
}
