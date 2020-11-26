package br.coop.cf.torcedores.exceptions;

public class TorcedorNotFoundException extends RuntimeException {

    public TorcedorNotFoundException() {

        super( "Torcedor não encontrado" );
    }
}
