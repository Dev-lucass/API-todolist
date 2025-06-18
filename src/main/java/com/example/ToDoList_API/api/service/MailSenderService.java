package com.example.ToDoList_API.api.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class MailSenderService {

     private final MailSender sender;
     private final JavaMailSender javaMailSender;

     public void sendEmail(String email) {
          SimpleMailMessage message = new SimpleMailMessage();
          message.setSentDate(Date.from(Instant.now()));
          message.setTo(email);
          message.setSubject("Succes authentication for login !");
          message.setText("Welcome to my to-do-list api");
          sender.send(message);
     }

     public void sendHtmlEmail(String email) throws MessagingException {
          MimeMessage mimeMessage = javaMailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

          helper.setSentDate(Date.from(Instant.now()));
          helper.setTo(email);
          helper.setSubject("🎯 Welcome to the To-Do List API!");

          String htmlContent = """
        <html>
            <head>
                <style>
                    body {
                        margin: 0;
                        padding: 0;
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        background: linear-gradient(135deg, #74ebd5 0%, #ACB6E5 100%);
                        color: #333;
                        text-align: center;
                    }
                    .container {
                        padding: 50px;
                    }
                    h1 {
                        color: #2C3E50;
                        font-size: 32px;
                    }
                    p {
                        font-size: 18px;
                        margin: 20px 0;
                    }
                    .footer {
                        margin-top: 40px;
                        font-size: 14px;
                        color: #555;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>Welcome to Your Productivity Hub 🚀</h2>
                    <p>Thanks for signing in! Your personal To-Do List API is ready to help you stay organized and achieve more each day.</p>
                    <p>Let’s turn your plans into actions — one task at a time.</p>
                    <div class="footer">
                        <p>Happy planning!<br>The To-Do List Team</p>
                    </div>
                </div>
            </body>
        </html>
    """;

          helper.setText(htmlContent, true); // true indicates HTML
          javaMailSender.send(mimeMessage);
     }

}
