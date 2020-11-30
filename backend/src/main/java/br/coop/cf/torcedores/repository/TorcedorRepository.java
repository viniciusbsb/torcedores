package br.coop.cf.torcedores.repository;

import br.coop.cf.torcedores.model.Torcedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TorcedorRepository extends RevisionRepository<Torcedor, Long, Integer>, JpaRepository<Torcedor, Long> {

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

    @Query( " select distinct T             " +
            " from Torcedor T               " +
            "   join fetch T.telefones TE   " +
            "   join fetch T.endereco E     " +
            " where T.cpf = :cpf            " +
            "    or T.nome = :nome         "   )
    List<Torcedor> findAllByCpfOrNome( String cpf, String nome );

    @Query( " select distinct T             " +
            " from Torcedor T               " +
            "   join fetch T.telefones TE   " +
            "   join fetch T.endereco E     " +
            " where T.cpf = :cpf            ")
    Optional<Torcedor> findTorcedorByCpf( String cpf );

}
