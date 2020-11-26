package br.coop.cf.torcedores.service;

import br.coop.cf.torcedores.repository.EnderecoRepository;
import br.coop.cf.torcedores.repository.TelefoneRepository;
import br.coop.cf.torcedores.repository.TorcedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TorcedorService {

    private TorcedorRepository torcedorRepository;
    private EnderecoRepository enderecoRepository;
    private TelefoneRepository telefoneRepository;

    @Autowired
    private void init( TorcedorRepository torcedorRepository,
                       EnderecoRepository enderecoRepository,
                       TelefoneRepository telefoneRepository ) {

        this.torcedorRepository = torcedorRepository;
        this.enderecoRepository = enderecoRepository;
        this.telefoneRepository = telefoneRepository;
    }




}
