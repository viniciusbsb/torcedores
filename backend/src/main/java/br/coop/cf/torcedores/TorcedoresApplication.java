package br.coop.cf.torcedores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@ComponentScan(basePackages = {"br.coop.cf.torcedores.controller", "br.coop.cf.torcedores.service"})
public class TorcedoresApplication {

    public static void main(String[] args) {
        SpringApplication.run(TorcedoresApplication.class, args);
    }

}
