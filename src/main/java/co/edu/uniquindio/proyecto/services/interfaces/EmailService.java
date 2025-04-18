package co.edu.uniquindio.proyecto.services.interfaces;

import co.edu.uniquindio.proyecto.dto.emailDTO.EmailDTO;

public interface EmailService {

    void enviarEmail(EmailDTO emailDTO) throws Exception;

    void enviarEmailConAdjunto(EmailDTO emailDTO, byte[] archivoAdjunto, String nombreArchivo) throws Exception;

}
