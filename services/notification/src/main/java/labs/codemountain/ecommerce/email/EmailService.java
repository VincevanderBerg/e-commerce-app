package labs.codemountain.ecommerce.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import labs.codemountain.ecommerce.kafka.order.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static labs.codemountain.ecommerce.email.EmailTemplate.ORDER_CONFIRMATION;
import static labs.codemountain.ecommerce.email.EmailTemplate.PAYMENT_CONFIRMATION;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_RELATED;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentNotificationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());

        messageHelper.setFrom("contact@codemountain.labs");
        final String templateName = PAYMENT_CONFIRMATION.getTemplateName();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderReference", orderReference);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        sendNotificationEmail(destinationEmail, mimeMessage, messageHelper, templateName, context);
    }

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail,
            String customerName,
            BigDecimal amount,
            String orderReference,
            List<Product> products
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_RELATED, UTF_8.name());

        messageHelper.setFrom("contact@codemountain.labs");
        final String templateName = ORDER_CONFIRMATION.getTemplateName();

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("products", products);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        sendNotificationEmail(destinationEmail, mimeMessage, messageHelper, templateName, context);
    }

    private void sendNotificationEmail(String destinationEmail, MimeMessage mimeMessage, MimeMessageHelper messageHelper, String templateName, Context context) {
        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info("Email successfully sent to {} with template {}", destinationEmail, templateName);
        } catch (MessagingException e) {
            log.warn("Cannot send email to {}", destinationEmail);
        }
    }
}
