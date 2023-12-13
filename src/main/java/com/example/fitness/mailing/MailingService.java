package com.example.fitness.mailing;

import com.example.fitness.commons.events.mailing.AdminEmailCreationEvent;
import com.example.fitness.commons.events.mailing.EmailCreationEvent;
import com.example.fitness.commons.events.mailing.EmailEvent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(MailingProperties.class)
public class MailingService {
    private final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final MailingProperties properties;
    private final MailSender emailSender;
    private Map<String, String> patterns;

    @PostConstruct
    void init() {
        patterns = Map.of(
                "#system", STR."system@\{properties.getDomain()}",
                "#admin", STR."\{properties.getAdmin()}@\{properties.getDomain()}",
                "#helpdesk", STR."\{properties.getHelpdesk()}@\{properties.getDomain()}"
        );
    }

    @TransactionalEventListener(EmailEvent.class)
    public void sendEmail(EmailEvent emailEvent) {
        replacePattern(emailEvent.getMailMessage());

        switch (emailEvent) {
            case AdminEmailCreationEvent ignore -> sendEmail(emailEvent, STR."\{properties.getAdmin()}@\{properties.getDomain()}");
            case EmailCreationEvent ignore -> sendEmail(emailEvent, STR."\{properties.getAdmin()}@\{properties.getDomain()}");
            default -> throw new IllegalStateException(STR."Unexpected value: \{emailEvent}");
        }
    }

    private void replacePattern(SimpleMailMessage email) {
        if (email.getFrom() != null && patterns.containsKey(email.getFrom())) {
            email.setFrom(patterns.get(email.getFrom()));
        }

        if (email.getTo() != null) {
            final var to = email.getTo();
            for (int i = 0; i < to.length; i++) {
                if (patterns.containsKey(to[i])) {
                    to[i] = patterns.get(to[i]);
                }
            }
            email.setTo(to);
        }
    }

    void sendEmail(EmailEvent emailEvent, String sender) {
        emailEvent.getMailMessage().setFrom(sender);
        executor.execute(() -> {
            log.info("Sending email: {}", emailEvent.getMailMessage());
            emailSender.send(emailEvent.getMailMessage());

            log.info("Email sent: {}", emailEvent.getMailMessage());
        });
    }

}
