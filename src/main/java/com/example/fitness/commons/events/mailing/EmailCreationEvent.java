package com.example.fitness.commons.events.mailing;

import org.springframework.mail.SimpleMailMessage;

public final class EmailCreationEvent extends EmailEvent {

    public EmailCreationEvent(Object source, SimpleMailMessage mailMessage) {
        super(source, mailMessage);
    }

}
