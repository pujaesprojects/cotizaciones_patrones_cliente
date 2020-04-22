package edu.puj.patrones.cliente.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String text) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);

        helper.setSubject(subject);
        helper.setText(text, true);

        javaMailSender.send(message);
    }

    public void sendEmail(String to) {
        String template = "<html><body><p>gracias</p><b>denada</b></body></html>";
        try {
            this.sendEmail(to, "Su Cotización ha sido recibida. ",
                    template);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
