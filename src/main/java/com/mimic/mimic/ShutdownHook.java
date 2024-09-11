package com.mimic.mimic;

import org.springframework.stereotype.Component;

@Component
public class ShutdownHook {

    public ShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("La aplicación está cerrando...");
        }));
    }
}
