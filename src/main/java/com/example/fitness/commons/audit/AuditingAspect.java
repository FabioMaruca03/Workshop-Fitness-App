package com.example.fitness.commons.audit;

import com.example.fitness.commons.events.mailing.AdminEmailCreationEvent;
import com.example.fitness.person.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.example.fitness.person.ApplicationRole.ADMIN;
import static com.example.fitness.person.ApplicationRole.DEVELOPER;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditingAspect {
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper mapper;

    @SneakyThrows
    @Before("execution(* com.example.fitness.person.PersonService.save(..))")
    public void log(@NonNull JoinPoint joinPoint) {
        if (joinPoint.getArgs()[0] instanceof Person person) {
            final var personJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(person);

            log.debug("Saving person: {}", personJson);
            if (Set.of(ADMIN, DEVELOPER).contains(person.getRole())) {
                log.info("High privileged user created: {} - {}", person.getEmail(), person.getRole());
                eventPublisher.publishEvent(new AdminEmailCreationEvent(this, highPrivilegedUserCreatedEmail(person)));
            }
        }
    }

    public SimpleMailMessage highPrivilegedUserCreatedEmail(Person person) {
        final var message = new SimpleMailMessage();

        message.setSubject("New high privileged user created");
        message.setText(STR."""
                The user\\s\{person.getName()} \{person.getSurname()} with email \{person.getEmail()} has been created.
                It appears to have\\s\{person.getRole()} role.
                """
        );
        message.setTo("#system");

        return message;
    }

}
