package com.example.fitness;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class FitnessApplicationTests {
    private final ApplicationModules rootModule = ApplicationModules.of(getClass().getPackageName());
    private final Documenter documenter = new Documenter(rootModule);

    @Test
    void contextLoads() {
    }

    @Test
    void architectureCoherence() {
        rootModule.forEach(System.out::println);
        rootModule.verify();
    }

    @Test
    void printArchitecture() {
        documenter.writeDocumentation();
    }

}
