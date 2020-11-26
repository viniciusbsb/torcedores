package br.coop.cf.torcedores.repository;

import br.coop.cf.torcedores.model.Telefone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends CrudRepository<Telefone, Long> {
}
