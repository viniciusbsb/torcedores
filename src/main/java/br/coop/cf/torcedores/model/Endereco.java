package br.coop.cf.torcedores.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Endereco implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( name = "nu_cep", nullable = false )
    private Integer cep = 99_999_999;

    @Column( name = "numero", length = 30 )
    private String numero;

    @Column( name = "de_logradouro", length = 80 )
    private String logradouro;

    @Column( name = "de_complemento", length = 150 )
    private String complemento;

    @Column( name = "de_bairro", length = 90 )
    private String bairro;

    @Column( name = "de_localidade", length = 30 )
    private String localidade;

    @Column( name = "sg_uf", length = 2 )
    private String uf;

}
