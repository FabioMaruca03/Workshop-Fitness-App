package com.example.fitness.commons.events.mailing;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;

import static com.example.fitness.commons.events.mailing.EmailEvent.EmailState.TO_SEND;

@Getter
@Setter
@ToString
public class EmailEvent extends ApplicationEvent {
    private final SimpleMailMessage mailMessage;
    private EmailState state = TO_SEND;

    public EmailEvent(Object source, @NonNull SimpleMailMessage mailMessage) {
        super(source);
        this.mailMessage = mailMessage;
    }

    public EmailEvent(Object source, @NonNull String from, @NonNull String subject, @NonNull String text, @NonNull String... to) {
        super(source);
        final var message = new SimpleMailMessage();

        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);
        message.setTo(to);

        this.mailMessage = message;
    }

    public enum EmailState {
        TO_SEND, SENT
    }

}
