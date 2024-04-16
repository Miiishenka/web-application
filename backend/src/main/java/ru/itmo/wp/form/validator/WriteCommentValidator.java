package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.JwtService;

@Component
public class WriteCommentValidator implements Validator {
    private final JwtService jwtService;

    public WriteCommentValidator(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public boolean supports(Class<?> clazz) {
        return CommentForm.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            CommentForm commentForm = (CommentForm) target;
            if (jwtService.find(commentForm.getJwt()) == null) {
                errors.reject("invalid-login-or-password", "Invalid jwt-token");
            }
        }
    }
}
