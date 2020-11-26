package br.coop.cf.torcedores.repository;

import br.coop.cf.torcedores.model.Torcedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TorcedorRepository extends JpaRepository<Torcedor, Long> {

    @Query( " select distinct T             " +
            " from Torcedor T               " +
            "   join fetch T.telefones TE   " +
            "   join fetch T.endereco E     " )
    List<Torcedor> findAllTorcedores();


    @Query( " select distinct T             " +
            " from Torcedor T               " +
            "   join fetch T.telefones TE   " +
            "   join fetch T.endereco E     " +
            " where T.id = :id              " )
    Optional<Torcedor> findTorcedorById(Long id );

}
