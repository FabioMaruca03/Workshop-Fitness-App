package com.example.fitness.mailing;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MailingQueues {

    @Bean
    Queue mailingQueue() {
        return QueueBuilder.durable("mailing")
                .withArgument("x-dead-letter-exchange", "mailing.dead")
                .withArgument("x-dead-letter-routing-key", "mailing")
                .build();
    }

    @Bean
    TopicExchange mailingExchange() {
        return new TopicExchange("mailing");
    }

    @Bean
    Binding binding(Queue mailingQueue, TopicExchange mailingExchange) {
        return BindingBuilder.bind(mailingQueue).to(mailingExchange).with("mailing");
    }

    @Bean
    Queue deadLetterQueue() {
        return new Queue("mailing.dead");
    }

    @Bean
    TopicExchange deadLetterExchange() {
        return new TopicExchange("mailing.dead");
    }

    @Bean
    Binding deadLetterBinding(Queue deadLetterQueue, TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("mailing");
    }

}