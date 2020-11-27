package br.coop.cf.torcedores.model;

import br.coop.cf.torcedores.listener.AuditoriaRevisionListener;
import lombok.Data;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@RevisionEntity(AuditoriaRevisionListener.class)
public class Auditoria extends DefaultRevisionEntity  {

    @Column( name = "user_name" )
    private String userName;

}
