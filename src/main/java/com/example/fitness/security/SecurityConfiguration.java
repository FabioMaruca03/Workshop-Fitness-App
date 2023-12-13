package com.example.fitness.security;

import com.example.fitness.person.Person;
import com.example.fitness.person.PersonService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;

import static com.example.fitness.person.ApplicationRole.ADMIN;
import static com.example.fitness.person.ApplicationRole.DEVELOPER;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, Environment environment) {
        httpSecurity
                .authorizeHttpRequests(ctx -> ctx
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/schedules/**").authenticated()
                        .requestMatchers("/home", "/login", "/logout").permitAll()
                        .requestMatchers("/styles/**", "/js/**").permitAll()
                        .requestMatchers("/tester/**").hasAnyRole(ADMIN.name(), DEVELOPER.name())
                )
                .rememberMe(rememberMe -> rememberMe
                        .tokenValiditySeconds(((int) Duration.ofDays(30).getSeconds()))
                        .rememberMeCookieName("fitness-cookie")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                )
                .userDetailsService(personService);

        if (environment.matchesProfiles("dev")) {
            httpSecurity
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(ctx -> ctx
                            .requestMatchers("/h2-console").permitAll()
                            .requestMatchers("/actuator/**").permitAll()
                    );
        } else {
            httpSecurity
                    .authorizeHttpRequests(ctx -> ctx
                            .requestMatchers("/h2-console").denyAll()
                            .requestMatchers("/actuator/**").hasAnyRole(ADMIN.name(), DEVELOPER.name())
                    );
        }

        return httpSecurity.build();
    }

    @PostConstruct
    void init() {
        try {
            personService.loadUserByUsername("admin@fitness.com");
        } catch (UsernameNotFoundException e) {
            log.info("Creating admin user: temporary password is 'admin'");
            final var admin = Person.builder()
                    .name("Administrator")
                    .surname("Fitness")
                    .email("admin@fitness.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(ADMIN)
                    .build();

            personService.save(admin);
            log.debug("Created admin user: {}", admin);
        }
    }

}
