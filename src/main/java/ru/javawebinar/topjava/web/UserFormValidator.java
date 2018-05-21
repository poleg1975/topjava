package ru.javawebinar.topjava.web;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;

public class UserFormValidator implements Validator {
    @Autowired
    @Qualifier("emailValidator")
    EmailValidator emailValidator;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if(!emailValidator.isValid(user.getEmail(), null)){
            errors.rejectValue("email", "Email already exists");
        }
    }
}
