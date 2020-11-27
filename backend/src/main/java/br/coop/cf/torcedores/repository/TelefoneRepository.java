package br.coop.cf.torcedores.repository;

import br.coop.cf.torcedores.model.Telefone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends RevisionRepository<Telefone, Long, Integer>,CrudRepository<Telefone, Long> {
}
