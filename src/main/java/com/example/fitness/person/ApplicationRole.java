package com.example.fitness.person;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum ApplicationRole {
    ADMIN,
    DEVELOPER,
    DIETOLOGIST,
    TRAINER,
    USER;

    private final Set<GrantedAuthority> authorities;

    ApplicationRole(Privilege... privileges) {
        this.authorities = Set.of(privileges)
                .stream()
                .flatMap(privilege -> privilege.getAuthority().stream())
                .collect(Collectors.toSet());

        this.authorities.add(new SimpleGrantedAuthority("ROLE_" + name()));
    }

    @Getter
    public enum Privilege {
        SCHEDULES("schedules::view", "schedules::create", "schedules::edit", "schedules::delete"),
        SCHEDULES_VIEW("schedules::view"),
        SCHEDULES_CREATE("schedules::create"),
        SCHEDULES_EDIT("schedules::edit"),
        SCHEDULES_DELETE("schedules::delete");

        private final Set<GrantedAuthority> authority;

        Privilege(String... authority) {
            this.authority = Stream.of(authority)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }

    }

}
