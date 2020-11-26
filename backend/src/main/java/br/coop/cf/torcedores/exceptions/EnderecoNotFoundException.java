package br.coop.cf.torcedores.exceptions;

public class EnderecoNotFoundException extends RuntimeException {

    public EnderecoNotFoundException() {

        super( "Endereço não localizado" );
    }
}
