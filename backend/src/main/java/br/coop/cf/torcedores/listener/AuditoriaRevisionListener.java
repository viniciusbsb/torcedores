package br.coop.cf.torcedores.listener;

import br.coop.cf.torcedores.model.Auditoria;
import br.coop.cf.torcedores.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.RevisionListener;

@Slf4j
public class AuditoriaRevisionListener implements RevisionListener {

    private final AuthenticationService authenticationService;

    AuditoriaRevisionListener(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @Override
    public void newRevision(Object o) {

        var userName = authenticationService.getAuthentication().getName();
        var auditoria = (Auditoria) o;
        auditoria.setUserName( userName );
        log.info( auditoria.toString() );
    }
}
