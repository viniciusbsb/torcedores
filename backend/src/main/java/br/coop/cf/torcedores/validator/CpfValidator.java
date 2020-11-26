package br.coop.cf.torcedores.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<Cpf, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValido = false;

        // considera-se erro CPF's formados por uma sequencia de numeros iguais

        boolean isCpfTemAlpha = cpf.chars().distinct().anyMatch( c -> !Character.isDigit( c ) );

        boolean isCpfValido = !isCpfTemAlpha && cpf.chars()
                .map( Character::getNumericValue )
                .boxed()
                .distinct()
                .count() > 1;

        if (isCpfValido && cpf.length() == 11) {

            int[] digitos = new int[cpf.length()];
            int somaNove = 0, somaDez = 0;
            int digitoDez, digitoOnze;

            for (int index = 0; index < cpf.length(); index++) {
                digitos[ index ] = Character.getNumericValue(cpf.charAt(index));

                if (index < 9) {
                    var valor = digitos[index] * (11 - (index + 1));
                    somaNove += valor;
                }

                if ( index < 10 ) {
                    var valor = digitos[index] * (12 - (index + 1));
                    somaDez += valor;
                }
            }

            digitoDez = 11 - (somaNove % 11);
            if (digitoDez > 9) {
                digitoDez = 0;
            }

            digitoOnze = 11 - (somaDez % 11);
            if (digitoOnze > 9) {
                digitoOnze = 0;
            }

            isValido = (digitoDez == digitos[9]) && (digitoOnze == digitos[10]);
        }

        return isValido;
    }

}
