package edu.puj.patrones.cliente.service;

import edu.puj.patrones.cliente.dto.ProviderDTO;
import edu.puj.patrones.cliente.dto.QuotationEmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private String getMessageTemplate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><body>");
        stringBuilder.append("<h3>Hola, Su cotización es:</h3>");
        stringBuilder.append("<p>#productos#</p>");
        stringBuilder.append("<p>#datosproveedor#</p>");
        stringBuilder.append("<p><b>$ #total#</b></p>");
        stringBuilder.append("</body></html>");
        return stringBuilder.toString();

    }

    public void sendEmail(String to, String subject, String text) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);

        helper.setSubject(subject);
        helper.setText(text, true);

        javaMailSender.send(message);
    }

    public void sendEmail(QuotationEmailDTO quotation) {
        HashMap<String, Object> params = new HashMap<>();

        StringBuilder productosBuilder = new StringBuilder();
        productosBuilder.append("<ul>");
        quotation.getQuotation().getProducts().stream()
                .forEach(product -> {
                    productosBuilder.append("<li>");
                    productosBuilder.append(String.format("%s : %d", product.getProductName(), product.getQuantity()));
                    productosBuilder.append("</li>");
                });
        productosBuilder.append("</ul>");

        StringBuilder providerBuilder = new StringBuilder();
        ProviderDTO providerInfo = quotation.getProvider();
        providerBuilder.append("<p>");
        providerBuilder.append(String.format("<b>Nombre:</b> %s<br/>", providerInfo.getName()));
        providerBuilder.append(String.format("<b>Dirección:</b> %s<br/>", providerInfo.getAddress()));
        providerBuilder.append(String.format("<b>Correo:</b> %s<br/>", providerInfo.getEmail()));
        providerBuilder.append("</p>");

        params.put("productos", productosBuilder.toString());
        params.put("datosproveedor", providerBuilder.toString());
        params.put("total", quotation.getTotal());

        try {
            this.sendEmail(quotation.getQuotation().getEmail(),
                    "Su Cotización ha sido recibida. ",
                    replaceTemplateParams(params));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String replaceTemplateParams(HashMap<String, Object> params) {
        String template = getMessageTemplate();
        params.forEach((param, value) -> {
            template.replaceAll(String.format("#%s#", param), value.toString());
        });
        return template;
    }
}
