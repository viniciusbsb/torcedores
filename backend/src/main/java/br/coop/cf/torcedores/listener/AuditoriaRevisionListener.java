package br.coop.cf.torcedores.listener;

import br.coop.cf.torcedores.model.Auditoria;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.RevisionListener;

@Slf4j
public class AuditoriaRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object o) {

        var auditoria = (Auditoria) o;
        auditoria.setUserName( "marcos" );
        log.info( auditoria.toString() );
    }
}
