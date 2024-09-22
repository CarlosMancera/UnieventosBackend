package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.EmailDTO;

public interface EmailService {

    void enviarCorreo(EmailDTO emailDTO) throws Exception;


}
