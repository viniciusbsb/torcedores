package br.coop.cf.torcedores.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Torcedor implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( name = "no_torcedor", nullable = false, length = 80)
    private String nome;

    @Column( name = "nu_cpf", nullable = false, length = 11 )
    private String cpf;

    @ManyToOne( fetch = FetchType.LAZY )
    private Endereco endereco;

    @OneToMany( fetch = FetchType.LAZY )
    private Set<Telefone> telefones;

}
