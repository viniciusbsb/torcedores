package br.coop.cf.torcedores.repository;

import br.coop.cf.torcedores.model.Torcedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TorcedorRepository extends JpaRepository<Torcedor, Long> {
}
