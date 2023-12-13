package com.example.fitness.commons.events.mailing;

import org.springframework.mail.SimpleMailMessage;

public final class AdminEmailCreationEvent extends EmailEvent {

    public AdminEmailCreationEvent(Object source, SimpleMailMessage mailMessage) {
        super(source, mailMessage);
    }

}
