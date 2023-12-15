package com.example.fitness.mailing;

import com.example.fitness.commons.events.mailing.EmailEvent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.fitness.commons.events.mailing.EmailEvent.EmailState.SENT;

@Slf4j
@Service
@EnableAsync
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
                "#system", "system@%s".formatted(properties.getDomain()),
                "#admin", "%s@%s".formatted(properties.getAdmin(), properties.getDomain()),
                "#helpdesk", "%s@%s".formatted(properties.getHelpdesk(), properties.getDomain())
        );
    }

    @Async
    @EventListener
    public void handleEmailEvents(EmailEvent emailEvent) {
        replacePattern(emailEvent.getMailMessage());

        executor.execute(() -> {
            log.debug("Sending email: {} to {}", emailEvent.getMailMessage().getFrom(), emailEvent.getMailMessage().getTo());
            emailSender.send(emailEvent.getMailMessage());

            log.debug("Email sent: {} to {}", emailEvent.getMailMessage().getFrom(), emailEvent.getMailMessage().getTo());
            emailEvent.setState(SENT);
        });
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

}
