package br.coop.cf.torcedores.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CpfValidator.class)
@Documented
public @interface Cpf {
    String message() default "Campo CPF inválido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
