package com.mark;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class ScripyXAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScripyXAdminApplication.class, args);
    }
}
