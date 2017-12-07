package net.nel.subclasses;

import net.nel.models.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class SignUpValidator implements Validator {
    @Autowired
    Environment env;

    public boolean supports(Class<?> aClass) {
        return false;
    }

    public void validate(Object target, Errors errors) {
        Login login = (Login) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, env.getProperty("LOGIN_VARIABLE"),
                env.getProperty("TAG_FOR_ERRORS"), env.getProperty("EMPTY_LOGIN"));
        if (errors.getFieldError(env.getProperty("LOGIN_VARIABLE")) == null) {
            Pattern loginPattern = Pattern.compile("^[A-Z0-9a-z._%+-]+@[A-Z0-9a-z.-]+\\.[A-Za-z]{2,6}$");
            if (!loginPattern.matcher(login.getLogin()).matches())
                errors.rejectValue(env.getProperty("LOGIN_VARIABLE"), env.getProperty("TAG_FOR_ERRORS"), env.getProperty("LOGIN_NOT_VALID"));
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, env.getProperty("PASSWORD_VARIABLE"),
                env.getProperty("TAG_FOR_ERRORS"), env.getProperty("EMPTY_PASSWORD"));
        if (errors.getFieldError(env.getProperty("PASSWORD_VARIABLE")) == null) {
            Pattern passwordPattern = Pattern.compile("^[A-Za-z0-9]{6,16}$");
            if (!passwordPattern.matcher(login.getPassword()).matches())
                errors.rejectValue(env.getProperty("PASSWORD_VARIABLE"), env.getProperty("TAG_FOR_ERRORS"), env.getProperty("PASSWORD_NOT_VALID"));
        }
    }
}
