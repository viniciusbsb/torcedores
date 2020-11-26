package br.coop.cf.torcedores.model;

import br.coop.cf.torcedores.constants.TipoTelefone;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
public class Telefone implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Integer id;

    @NotBlank( message = "Telefone é obrigatório" )
    @Column( name = "nu_telefone", nullable = false, length = 11 )
    private String telefone;

    @NotNull( message = "Tipo de telefone é obrigatório" )
    @Column( name = "tp_telefone", nullable = false )
    @Enumerated( EnumType.STRING )
    private TipoTelefone tipoTelefone;

    @Column( name = "ic_principal", nullable = false )
    private Boolean isPrincipal;
}
