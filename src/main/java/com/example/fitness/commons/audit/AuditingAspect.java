package com.example.fitness.commons.audit;

import com.example.fitness.person.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditingAspect {
    private final ObjectMapper mapper;

    @SneakyThrows
    @Before("execution(* com.example.fitness.person.PersonService.save(..))")
    public void log(JoinPoint joinPoint) {
        if (joinPoint.getArgs()[0] instanceof Person person) {
            log.info("Saving person: {}", mapper.writerWithDefaultPrettyPrinter().writeValueAsString(person));
        }
    }

}
