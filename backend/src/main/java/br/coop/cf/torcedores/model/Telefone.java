package br.coop.cf.torcedores.model;

import br.coop.cf.torcedores.constants.TipoTelefone;
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
public class Telefone implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @Column( name = "nu_telefone" )
    private String telefone;

    @Column( name = "tp_telefone" )
    @Enumerated( EnumType.STRING )
    private TipoTelefone tipoTelefone;

    @Column( name = "ic_principal" )
    private Boolean isPrincipal;
}
