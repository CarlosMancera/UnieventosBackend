package co.edu.uniquindio.proyecto.services.implement;

import co.edu.uniquindio.proyecto.services.interfaces.EmailService;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    Email email = EmailBuilder.startingBlank()
            .from("unieventos20242@gmail.com")
            .to(emailDTO.destinatario())
            .withSubject(emailDTO.asunto())
            .withPlainText(emailDTO.cuerpo())
            .buildEmail();


       try (Mailer mailer = MailerBuilder
            .withSMTPServer("smtp.gmail.com", 587, "unieventos20242@gmail.com", "agnv ejjt kzqx neoz")
            .withTransportStrategy(TransportStrategy.SMTP_TLS)
            .withDebugLogging(true)
            .buildMailer()) {


        mailer.sendMail(email);
    }


}


