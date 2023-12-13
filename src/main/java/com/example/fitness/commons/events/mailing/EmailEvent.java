package com.example.fitness.commons.events.mailing;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;

@Getter
public sealed class EmailEvent extends ApplicationEvent permits AdminEmailCreationEvent, EmailCreationEvent {
    private final SimpleMailMessage mailMessage;

    public EmailEvent(Object source, @NonNull SimpleMailMessage mailMessage) {
        super(source);
        this.mailMessage = mailMessage;
    }
}
