package peaksoft.instagram.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import peaksoft.instagram.repository.UserRepo;



@Component
@RequiredArgsConstructor
public class UserNameValidator implements ConstraintValidator<UserName, String> {

    private final UserRepo userRepo;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        if (username == null || username.isBlank()) {
            return false;
        }

        return !userRepo.existsByuserName(username);
    }

}