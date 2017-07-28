package com.pp.domain.validator;

import com.pp.domain.task.TaskCreateForm;
import com.pp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by astepanov
 */
@Component
public class TaskCreateFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskCreateFormValidator.class);
    private final UserService userService;

    @Autowired
    public TaskCreateFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(TaskCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        TaskCreateForm form = (TaskCreateForm) target;
        validateTitle(errors, form);
        validateDescription(errors, form);
    }

    private void validateDescription(Errors errors, TaskCreateForm form) {
        if(form.getDescription() == null || form.getDescription().length() < 30){
            errors.reject("description.too_short", "Description is too short");
        }
    }

    private void validateTitle(Errors errors, TaskCreateForm form) {
        if(form.getDescription() == null || form.getDescription().length() < 10){
            errors.reject("title.too_short", "Title is too short");
        }
    }
}
