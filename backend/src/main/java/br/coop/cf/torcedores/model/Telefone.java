package br.coop.cf.torcedores.model;

import br.coop.cf.torcedores.constants.TipoTelefone;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
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

    @JsonIgnore
    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn( name = "id_torcedor" )
    private Torcedor torcedor;
}
