package br.coop.cf.torcedores.service;

import br.coop.cf.torcedores.exceptions.TorcedorNotFoundException;
import br.coop.cf.torcedores.model.Torcedor;
import br.coop.cf.torcedores.repository.TorcedorRepository;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TorcedorService {

    private final TorcedorRepository torcedorRepository;
    private final KafkaService kafkaService;

    TorcedorService( TorcedorRepository torcedorRepository,
                     KafkaService kafkaService ) {

        this.torcedorRepository = torcedorRepository;
        this.kafkaService = kafkaService;
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

        if ( !cpf.isEmpty() || !nome.isEmpty() ) {

            return torcedorRepository.findAllByCpfOrNome(cpf, nome);
        }else{

            return torcedorRepository.findAll();
        }
    }

    public Torcedor save( Torcedor torcedor ) {

        torcedor.getTelefones()
                .forEach( t -> t.setTorcedor( torcedor ) );

        var retorno = torcedorRepository.save( torcedor );
        kafkaService.sendMessageCadastro( retorno );

        return retorno;
    }

    public Torcedor update( Long id, Torcedor torcedor ) {

        torcedor.setId( id );
        torcedor.getTelefones()
                .forEach( t -> t.setTorcedor( torcedor ) );


        return torcedorRepository.save( torcedor );
    }

    public void delete( Long id ) {

        var torcedor = torcedorRepository.findById( id ).orElseThrow( TorcedorNotFoundException::new );
        torcedorRepository.deleteById( id );
        kafkaService.sendMessageDesligado( torcedor );
    }

}
