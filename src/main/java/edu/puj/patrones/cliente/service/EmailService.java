package edu.puj.patrones.cliente.service;

import com.github.javafaker.Faker;
import edu.puj.patrones.cliente.dto.ProviderDTO;
import edu.puj.patrones.cliente.dto.QuotationEmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private String getMessageTemplate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><body>");
        stringBuilder.append("<h3>Hola, Su cotización de No: #id# es:</h3>");
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
        Faker faker = new Faker(new Locale("es"));
        var ref = new Object() {
            Double price = quotation.getTotal();
            Integer index = 1;
            Integer size = quotation.getQuotation().getProducts().size();
        };

        NumberFormat nf = NumberFormat.getInstance();
        StringBuilder productosBuilder = new StringBuilder();
        productosBuilder.append("<ul>");
        quotation.getQuotation().getProducts()
            .forEach(product -> {
                Double price = faker.number().randomDouble(2, 100000, ref.price.longValue());
                if(ref.size == ref.index) {
                   price = ref.price;
                }
                ref.price -= price;
                productosBuilder.append("<li>");
                productosBuilder.append(String.format("%s : %d - Precio: \\$ %s",
                    product.getProductName(), product.getQuantity(), nf.format(price)));
                productosBuilder.append("</li>");
                ref.index++;
            });
        productosBuilder.append("</ul>");

        StringBuilder providerBuilder = new StringBuilder();
        ProviderDTO providerInfo = quotation.getProvider();
        providerBuilder.append("<p>");
        providerBuilder.append(String.format("<b>Nombre:</b> %s<br/>", providerInfo.getName()));
        providerBuilder.append(String.format("<b>Dirección:</b> %s<br/>", providerInfo.getAddress()));
        providerBuilder.append(String.format("<b>Correo:</b> %s<br/>", providerInfo.getEmail()));
        providerBuilder.append("</p>");

        params.put("id", quotation.getQuotation().getId().toString());
        params.put("productos", productosBuilder.toString());
        params.put("datosproveedor", providerBuilder.toString());
        params.put("total", nf.format(quotation.getTotal()));

        try {
            this.sendEmail(
                quotation.getQuotation().getEmail(),
                "Su Cotización ha sido recibida. ",
                replaceTemplateParams(params)
            );
        } catch (MessagingException e) {
            log.error("Error enviando el email", e);
        }
    }

    private String replaceTemplateParams(HashMap<String, Object> params) {
        var ref = new Object() {
            String template = getMessageTemplate();
        };
        params.forEach((param, value) -> {
            ref.template = ref.template.replaceAll(String.format("#%s#", param), value.toString());
        });
        return ref.template;
    }
}
