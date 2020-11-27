package br.coop.cf.torcedores.repository;

import br.coop.cf.torcedores.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends RevisionRepository<Endereco, Long, Integer>, JpaRepository<Endereco, Long> {

}
