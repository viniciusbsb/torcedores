package br.coop.cf.torcedores.repository;

import br.coop.cf.torcedores.model.Endereco;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, Long> {

}
