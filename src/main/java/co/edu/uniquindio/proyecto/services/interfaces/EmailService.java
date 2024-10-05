package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.emailDTO.EmailDTO;
import org.springframework.stereotype.Service;

public interface EmailService {

    void enviarEmail(EmailDTO emailDTO) throws Exception;
}
