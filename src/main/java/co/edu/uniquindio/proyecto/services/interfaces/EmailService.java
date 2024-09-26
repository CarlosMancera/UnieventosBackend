package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.emailDTO.EmailDTO;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void enviarCorreo(EmailDTO emailDTO) throws Exception;
}
