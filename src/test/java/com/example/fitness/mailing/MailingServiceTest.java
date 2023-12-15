package com.example.fitness.mailing;

import com.example.fitness.commons.events.mailing.EmailEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;

import static com.example.fitness.commons.events.mailing.EmailEvent.EmailState.SENT;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Slf4j
@MockBeans(@MockBean(MailSender.class))
@ApplicationModuleTest(webEnvironment = NONE)
class MailingServiceTest {

    @Test
    void shouldSendEmail(Scenario scenario) {
        scenario.publish(() -> new EmailEvent(this, "#system", "Test email", "This is a test email", "#admin"))
                .andWaitForEventOfType(EmailEvent.class)
                .matching(event -> event.getState().equals(SENT))
                .toArriveAndVerify(event -> {
                    log.info("Event detected: {}", event);
                    assertEquals(SENT, event.getState());
                    assertEquals("system@fitness.com", event.getMailMessage().getFrom());
                    assertArrayEquals(new String[] {"admin@fitness.com"}, event.getMailMessage().getTo());
                });
    }

    @TestConfiguration
    static class TestConfigs {

    }

}