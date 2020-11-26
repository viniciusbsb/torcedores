package br.coop.cf.torcedores.exceptions;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException() {

        super();
    }

    public ServiceUnavailableException( String message ) {

        super( message );
    }

    public ServiceUnavailableException( String message, Throwable th ) {

        super( message, th );
    }

}
