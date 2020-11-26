package br.coop.cf.torcedores.controller;

import br.coop.cf.torcedores.model.Endereco;
import br.coop.cf.torcedores.service.EnderecoService;
import br.coop.cf.torcedores.service.KafkaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "endereco" )
public class EnderecoController {

    private final EnderecoService enderecoService;
    private final KafkaService kafkaService;

    EnderecoController( EnderecoService enderecoService, KafkaService kafkaService ) {
        this.enderecoService = enderecoService;
        this.kafkaService = kafkaService;
    }


    @GetMapping( "/integracao/{cep}" )
    public ResponseEntity<Endereco> findEnderecoByCep(@PathVariable( "cep" ) String cep ) {

        var endereco = enderecoService.findEnderecoByCep( cep );
        return ResponseEntity.status( HttpStatus.OK ).body( endereco );
    }

    @GetMapping( "/send/{msg}" )
    public ResponseEntity<Void> sendMessage(@PathVariable( "msg" ) String msg ) {

        kafkaService.sendMessage( msg );
        return ResponseEntity.status( HttpStatus.NO_CONTENT ).build();
    }

}
