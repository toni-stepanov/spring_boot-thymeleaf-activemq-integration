package com.pp.controller;

import com.pp.domain.page.PageList;
import com.pp.domain.page.PageNumber;
import com.pp.domain.page.PageWrapper;
import com.pp.domain.task.Task;
import com.pp.domain.task.TaskCreateForm;
import com.pp.domain.validator.TaskCreateFormValidator;
import com.pp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by astepanov
 */
@Controller
@RequiredArgsConstructor
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    private static final int BUTTONS_TO_SHOW = 5;
    private final TaskService taskService;
    private final TaskCreateFormValidator taskCreateFormValidator;

    @InitBinder("form")
    public void initBinder(WebDataBinder dataBinder){
        dataBinder.addValidators(taskCreateFormValidator);
    }

    @GetMapping("/tasks")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> number) {
        LOGGER.debug(number.isPresent() ? "getting task page with number : " + number : "getting first task page");
        final ModelAndView modelAndView = new ModelAndView("tasks");
        final Page<Task> page = taskService.findAllPageable(new PageRequest((new PageNumber(number))
                .evalPageNumber(number), pageSize.orElse(BUTTONS_TO_SHOW)));
        final PageWrapper<Task> pageWrapper = new PageWrapper<>(page);
        final PageList<Task> pageList = new PageList<>(pageWrapper, BUTTONS_TO_SHOW);
        pageList.initializeBounds();
        modelAndView.addObject("page", pageWrapper);
        modelAndView.addObject("pageList", pageList);
        return modelAndView;
    }

    @GetMapping(value = "/task/create")
    private ModelAndView getNewTask(){
        LOGGER.debug("Getting task create form");
        return new ModelAndView("newTask", "form", new TaskCreateForm());
    }

    @PostMapping(value = "/task/create")
    private String createNewTask(@Valid @ModelAttribute("form") TaskCreateForm form, BindingResult bindingResult){
        LOGGER.debug("Processing task create form={}, bindingResult={}", form, bindingResult);
        if (bindingResult.hasErrors()) {
            // failed validation
            return "newTask";
        }
        try {
            taskService.create(form);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the task", e);
            bindingResult.reject("form.saving", "Problem with saving");
            return "newTask";
        }
        // ok, redirect
        return "redirect:/home";
    }

}
