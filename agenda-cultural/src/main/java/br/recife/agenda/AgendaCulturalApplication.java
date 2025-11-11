package br.recife.agenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.recife.agenda.codigo")
public class AgendaCulturalApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgendaCulturalApplication.class, args);
    }
}
 