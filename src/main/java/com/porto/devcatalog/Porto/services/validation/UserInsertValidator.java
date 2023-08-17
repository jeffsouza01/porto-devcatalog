package com.porto.devcatalog.Porto.services.validation;


import com.porto.devcatalog.Porto.DTO.UserCreateDTO;
import com.porto.devcatalog.Porto.controllers.exceptions.FieldMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserCreateDTO> {

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserCreateDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        // Coloque aqui seus testes de validação, acrescentando objetos FieldMessage à lista

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
