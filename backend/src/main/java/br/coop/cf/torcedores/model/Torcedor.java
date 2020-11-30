package br.coop.cf.torcedores.model;

import br.coop.cf.torcedores.validator.Cpf;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Audited
public class Torcedor implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id_torcedor" )
    private Long id;

    @NotBlank( message = "Nome do torcedor é obrigatório" )
    @Column( name = "no_torcedor", nullable = false, length = 80)
    private String nome;

    @NotBlank( message = "Nome do torcedor é obrigatório" )
    @Column( name = "de_email", nullable = false, length = 80)
    private String email;

    @Cpf( message = "CPF inválido" )
    @NotBlank( message = "CPF é obrigatório" )
    @Column( name = "nu_cpf", nullable = false, length = 11, unique = true )
    private String cpf;

    @ManyToOne( fetch = FetchType.LAZY, cascade = {CascadeType.ALL} )
    @JoinColumn( name = "id_endereco" )
    private Endereco endereco;

    @OneToMany( fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "torcedor", orphanRemoval = true )
    private Set<Telefone> telefones;

}
