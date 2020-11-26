package br.coop.cf.torcedores.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:config.properties")
public class ConfigProperties {

    @Value( "${url_servico}" )
    private String urlServico;
}
