package br.coop.cf.torcedores.controller;

import br.coop.cf.torcedores.model.Endereco;
import br.coop.cf.torcedores.service.EnderecoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "enderecos" )
public class EnderecoController {

    private final EnderecoService enderecoService;

    EnderecoController( EnderecoService enderecoService ) {
        this.enderecoService = enderecoService;
    }


    @GetMapping( "/integracao/{cep}" )
    public ResponseEntity<Endereco> findEnderecoByCep(@PathVariable( "cep" ) String cep ) {

        var endereco = enderecoService.findEnderecoByCep( cep );
        return ResponseEntity.status( HttpStatus.OK ).body( endereco );
    }


}
