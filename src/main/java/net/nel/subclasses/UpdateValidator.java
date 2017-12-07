package net.nel.subclasses;

import net.nel.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UpdateValidator implements Validator {
    @Autowired
    Environment env;

    public boolean supports(Class<?> aClass) {
        return false;
    }

    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, env.getProperty("BOOKNAME_VARIABLE"),
                env.getProperty("TAG_FOR_ERRORS"), env.getProperty("EMPTY_BOOKNAME"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, env.getProperty("AUTHOR_VARIABLE"),
                env.getProperty("TAG_FOR_ERRORS"), env.getProperty("EMPTY_AUTHOR"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, env.getProperty("DESCRIPTION_VARIABLE"),
                env.getProperty("TAG_FOR_ERRORS"), env.getProperty("EMPTY_DESCRITION"));
    }
}
