package com.example.fitness.mailing;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mailing")
public class MailingProperties {
    private String domain;
    private String admin;
    private String helpdesk;
}