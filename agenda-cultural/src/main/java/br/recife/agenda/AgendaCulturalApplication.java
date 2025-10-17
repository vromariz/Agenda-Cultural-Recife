package br.recife.agenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgendaCulturalApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgendaCulturalApplication.class, args);
        System.out.println("🚀 Servidor Spring Boot rodando em http://localhost:8080");
    }
}
