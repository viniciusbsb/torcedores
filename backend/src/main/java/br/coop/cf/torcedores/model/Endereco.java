package br.coop.cf.torcedores.model;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Audited
public class Endereco implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id_endereco" )
    private Long id;

    @NotNull( message = "Cep é obrigatório" )
    @Column( name = "nu_cep", nullable = false )
    private String cep;

    @NotBlank( message = "Número é obrigatório" )
    @Column( name = "numero", length = 30, nullable = false )
    private String numero;

    @NotBlank( message = "Logradouro é obrigatório" )
    @Column( name = "de_logradouro", nullable = false, length = 80 )
    private String logradouro;

    @NotBlank( message = "Complemento é obrigatório" )
    @Column( name = "de_complemento", nullable = false, length = 150 )
    private String complemento;

    @NotBlank( message = "Bairro é obrigatório" )
    @Column( name = "de_bairro", nullable = false, length = 90 )
    private String bairro;

    @NotBlank( message = "Localidade é obrigatório" )
    @Column( name = "de_localidade", nullable = false, length = 30 )
    private String localidade;

    @NotBlank( message = "UF é obrigatório" )
    @Column( name = "sg_uf", nullable = false, length = 2 )
    private String uf;

}
