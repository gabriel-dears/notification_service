package com.hospital_app.notification_service.infra.adapter.out.email;

import com.hospital_app.notification_service.application.port.out.email.EmailInput;
import com.hospital_app.notification_service.application.port.out.email.EmailSender;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender mailSender;

    public EmailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Retry(name = "emailRetry", fallbackMethod = "sendFallback")
    public void send(EmailInput emailInput) {
        try {
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(emailInput.to());
            helper.setSubject(emailInput.subject());
            helper.setText(emailInput.htmlContent(), true);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send appointment email", e);
        }
    }

    // fallback method
    @SuppressWarnings("unused")
    public void sendFallback(EmailInput emailInput, Throwable t) {
        System.err.println("Failed to send email to " + emailInput.to() + ": " + t.getMessage());
    }

}
