package br.coop.cf.torcedores.service;

import br.coop.cf.torcedores.exceptions.TorcedorNotFoundException;
import br.coop.cf.torcedores.model.Torcedor;
import br.coop.cf.torcedores.repository.EnderecoRepository;
import br.coop.cf.torcedores.repository.TelefoneRepository;
import br.coop.cf.torcedores.repository.TorcedorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TorcedorService {

    private final TorcedorRepository torcedorRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    TorcedorService( TorcedorRepository torcedorRepository,
                       EnderecoRepository enderecoRepository,
                       TelefoneRepository telefoneRepository ) {

        this.torcedorRepository = torcedorRepository;
        this.enderecoRepository = enderecoRepository;
        this.telefoneRepository = telefoneRepository;
    }

    public Torcedor findById( Long id ) {

        return torcedorRepository.findTorcedorById( id )
                .orElseThrow(TorcedorNotFoundException::new);
    }

    public Iterable<Torcedor> findAll() {

        return torcedorRepository.findAllTorcedores();
    }

    public Iterable<Torcedor> findByExample( Torcedor torcedor ) {

        return torcedorRepository.findAll( Example.of( torcedor ) );
    }

    public Iterable<Torcedor> findByCpfOrNome( String cpf, String nome ) {

        return torcedorRepository.findAllByCpfOrNome( cpf, nome );
    }



    public Torcedor save( Torcedor torcedor ) {

        return torcedorRepository.save( torcedor );
    }

    public Torcedor update( Long id, Torcedor torcedor ) {

        torcedor.setId( id );
        return torcedorRepository.save( torcedor );
    }

    public void delete( Long id ) {

        torcedorRepository.deleteById( id );
    }

}
