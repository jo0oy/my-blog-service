package com.jo0oy.myblog.validation;

import com.jo0oy.myblog.web.form.JoinForm;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {

    @Test
    void phoneNumberFormatValidationTest() {

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        JoinForm joinForm = new JoinForm("", "test1234!@", "sadfkajs@naver.com",
                "이주연", "01-4444-2222");

        Set<ConstraintViolation<JoinForm>> violations = validator.validate(joinForm);

        for (ConstraintViolation<JoinForm> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("message = " + violation.getMessage());
        }

    }
}
