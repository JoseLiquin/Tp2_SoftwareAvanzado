package com.example.tp2.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value; // <-- Importar Value
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${email}") // <-- Inyecta el correo configurado en las variables de entorno
    private String remitente;

    @Async // Procesa el envío en un hilo secundario sin bloquear el flujo principal
    public void enviarCorreoHtmlAsincrono(String destinatario, String asunto, String contenidoHtml) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(remitente); // <-- ¡Línea clave para solucionar el error!
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(contenidoHtml, true); // Formato HTML real[cite: 10]

            mailSender.send(message);
            log.info("Correo HTML enviado asíncronamente con éxito a: {}", destinatario);
        } catch (MessagingException e) {
            log.error("Error al enviar el correo electrónico asíncrono a {}: {}", destinatario, e.getMessage());
        }
    }
}