package com.pp.controller;

import com.pp.domain.page.PageList;
import com.pp.domain.page.PageNumber;
import com.pp.domain.page.PageWrapper;
import com.pp.domain.user.User;
import com.pp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

/**
 * Created by astepanov
 */
@Controller
@RequiredArgsConstructor
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    private static final int BUTTONS_TO_SHOW = 5;
    private final UserService userService;

    @GetMapping("/users")
    public ModelAndView showPersonsPage(@RequestParam("pageSize") Optional<Integer> pageSize,
                                        @RequestParam("page") Optional<Integer> number) {
        final ModelAndView modelAndView = new ModelAndView("users");
        final Page<User> page = userService.findAllPageable(new PageRequest((new PageNumber(number))
                .evalPageNumber(number), pageSize.orElse(BUTTONS_TO_SHOW)));
        final PageWrapper<User> pageWrapper = new PageWrapper<>(page);
        final PageList<User> pageList = new PageList<>(pageWrapper, BUTTONS_TO_SHOW);
        pageList.initializeBounds();
        modelAndView.addObject("page", pageWrapper);
        modelAndView.addObject("pageList", pageList);
        return modelAndView;
    }

}
