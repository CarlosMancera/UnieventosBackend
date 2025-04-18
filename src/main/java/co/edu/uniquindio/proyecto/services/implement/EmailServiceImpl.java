package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.emailDTO.EmailDTO;
import co.edu.uniquindio.proyecto.services.interfaces.EmailService;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    public void enviarEmail(EmailDTO emailDTO) throws Exception {
        Email email = EmailBuilder.startingBlank()
                .from("unieventos20242@gmail.com")
                .to(emailDTO.destinatario())
                .withSubject(emailDTO.asunto())
                .withPlainText(emailDTO.cuerpo())
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "unieventos20242@gmail.com", "cwqp yoxa xrlf ndmt") // 🔥 Nueva contraseña
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withSessionTimeout(10_000)
                .withDebugLogging(true)
                .buildMailer()) {

            mailer.sendMail(email);
        }

    }

    @Override
    public void enviarEmailConAdjunto(EmailDTO emailDTO, byte[] archivoAdjunto, String nombreArchivo) throws Exception {
        Email email = EmailBuilder.startingBlank()
                .from("unieventos20242@gmail.com")
                .to(emailDTO.destinatario())
                .withSubject(emailDTO.asunto())
                .withPlainText(emailDTO.cuerpo())
                .withAttachment(nombreArchivo, new ByteArrayDataSource(archivoAdjunto, "application/pdf"))
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "unieventos20242@gmail.com", "cwqp yoxa xrlf ndmt")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withSessionTimeout(10_000)
                .withDebugLogging(true)
                .buildMailer()) {

            mailer.sendMail(email);
        }
    }
}
