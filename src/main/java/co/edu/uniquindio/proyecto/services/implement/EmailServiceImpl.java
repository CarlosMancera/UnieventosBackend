package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.dto.emailDTO.EmailDTO;
import co.edu.uniquindio.proyecto.services.interfaces.EmailService;
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


    Email email = EmailBuilder.startingBlank()
            .from("unieventos20242@gmail.com")
            .to(EmailDTO.destinatario())
            .withSubject(EmailDTO.asunto())
            .withPlainText(EmailDTO.cuerpo())
            .buildEmail();


       try (Mailer mailer = MailerBuilder
            .withSMTPServer("smtp.gmail.com", 587, "unieventos20242@gmail.com", "agnv ejjt kzqx neoz")
            .withTransportStrategy(TransportStrategy.SMTP_TLS)
            .withDebugLogging(true)
            .buildMailer()) {


        mailer.sendMail(email);
    }


    @Override
    public void enviarCorreo(EmailDTO emailDTO) throws Exception {

    }
}
