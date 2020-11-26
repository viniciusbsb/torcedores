package br.coop.cf.torcedores.service;

import br.coop.cf.torcedores.exceptions.EnderecoNotFoundException;
import br.coop.cf.torcedores.exceptions.ServiceUnavailableException;
import br.coop.cf.torcedores.model.Endereco;
import br.coop.cf.torcedores.repository.EnderecoRepository;
import br.coop.cf.torcedores.util.ConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ConfigProperties configProperties;

    EnderecoService(EnderecoRepository enderecoRepository, ConfigProperties configProperties){
        this.enderecoRepository = enderecoRepository;
        this.configProperties = configProperties;
    }

    public Endereco findEnderecoByCep(String cep) {
        Endereco retorno;

        try {
            var restTemplate = new RestTemplate();
            var url = MessageFormat.format(configProperties.getUrlServico(), cep);
            ResponseEntity<Endereco> response = restTemplate.getForEntity(url, Endereco.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {

                retorno = Optional.ofNullable( response.getBody() )
                        .orElseThrow( EnderecoNotFoundException::new ) ;

                if ( retorno.getCep() == null) {

                    throw new EnderecoNotFoundException();
                }
            } else {

                throw new ServiceUnavailableException("Serviço para localizar o endereço esta indisponível no momento");
            }
        }catch( RestClientException e ) {

            throw new ServiceUnavailableException( e.getMessage(), e );
        }

        return retorno;
    }

}
