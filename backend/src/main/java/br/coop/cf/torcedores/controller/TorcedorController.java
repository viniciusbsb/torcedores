package br.coop.cf.torcedores.controller;

import br.coop.cf.torcedores.model.Torcedor;
import br.coop.cf.torcedores.service.TorcedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "torcedores" )
public class TorcedorController {

    private TorcedorService torcedorService;

    TorcedorController( TorcedorService torcedorService ) {
        this.torcedorService = torcedorService;
    }

    @GetMapping( "/" )
    public ResponseEntity<Iterable<Torcedor>> findAll() {

        var torcedores = torcedorService.findAll();
        return ResponseEntity.status( HttpStatus.OK ).body( torcedores );
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<Iterable<Torcedor>> findById( @PathVariable( "id" ) Long id ) {

        var torcedores = torcedorService.findAll();
        return ResponseEntity.status( HttpStatus.OK ).body( torcedores );
    }


    @PostMapping( "/" )
    public ResponseEntity<Torcedor> save(@RequestBody Torcedor torcedor) {

        var torcedorSalvo = torcedorService.save( torcedor );
        return ResponseEntity.status( HttpStatus.OK ).body( torcedorSalvo );
    }

    @PutMapping( "/{id}" )
    public ResponseEntity<Torcedor> update(@PathVariable( "id" ) Long id, @RequestBody Torcedor torcedor) {

        var torcedorSalvo = torcedorService.update( id, torcedor );
        return ResponseEntity.status( HttpStatus.OK ).body( torcedorSalvo );
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<Void> delete(@PathVariable( "id" ) Long id) {

        torcedorService.delete( id );
        return ResponseEntity.noContent().build();
    }


}
