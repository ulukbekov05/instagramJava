package peaksoft.instagram.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNameValidator.class)
public @interface UserName {

    String message() default "{userName должен быть уникалным этод userName занать  }";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
