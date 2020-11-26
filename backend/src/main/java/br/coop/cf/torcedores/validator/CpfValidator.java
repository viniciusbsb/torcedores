package br.coop.cf.torcedores.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CpfValidator implements ConstraintValidator<Cpf, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValido = false;

        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        boolean isCpfInvalido = cpf.chars().noneMatch(c -> c == cpf.charAt(0) && Character.isDigit(c));

        if (!isCpfInvalido && cpf.length() == 11) {

            int[] digitos = new int[cpf.length()];
            // Variáveis temporárias
            int somaNove = 0, somaDez = 0;
            // Os dígitos de verificação do CPF
            int digitoDez, digitoOnze;

            // Transforma os caracteres em dígitos numéricos
            for (int index = 0; index < cpf.length(); index++) {
                digitos[ index ] = Character.getNumericValue(cpf.charAt(index));
                // Soma os nove primeiros dígitos multiplicados
                // por um valor determinado.

                if (index < 9) {
                    var valor = digitos[index] * (11 - (index + 1));
                    somaNove += valor;
                }

                if ( index < 10 ) {
                    var valor = digitos[index] * (12 - (index + 1));
                    somaDez += valor;
                }
            }

            // Usa a soma para calcular o primeiro dígito verificador.
            digitoDez = 11 - (somaNove % 11);
            if (digitoDez > 9) {
                digitoDez = 0;
            }

            // Usa a soma para calcular o segundo dígito verificador.
            digitoOnze = 11 - (somaDez % 11);
            if (digitoOnze > 9) {
                digitoOnze = 0;
            }

            isValido = (digitoDez == digitos[9]) && (digitoOnze == digitos[10]);
        }

        return isValido;
    }

}
