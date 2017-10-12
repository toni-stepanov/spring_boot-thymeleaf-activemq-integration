package com.pp.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.pp.controller.TaskController;
import com.pp.controller.advice.CurrentUserControllerAdvice;
import com.pp.domain.task.Task;
import com.pp.domain.user.CurrentUser;
import com.pp.domain.user.Role;
import com.pp.domain.user.User;
import com.pp.domain.validator.TaskCreateFormValidator;
import com.pp.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by astepanov
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
@ActiveProfiles("test")
public class TaskControllerIntegrationTest {

	@Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private WebClient webClient;
    private final int PAGE_NUMBER = 1;
    private final String PAGE_NUMBER_STRING = "1";
    private final int PAGE_SIZE = 5;
    private final String PAGE_SIZE_STRING = "5";
    private final String SEARCH_TERM = "itl";
    private Pageable pageRequest;
    private User user;

    @MockBean
    private TaskCreateFormValidator formValidator;

    @MockBean
    private CurrentUserControllerAdvice currentUserControllerAdvice;

    @MockBean
    private TaskService taskService;

    @Before
    public void setUp() throws Exception {
        user = new User(1L, "test@test.com", "124214", Role.ADMIN, new ArrayList<Task>());
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity())
            .build();
        when(taskService.findAllPageable(any())).thenReturn(createEmptyPage());
        when(currentUserControllerAdvice.getCurrentUser(any())).thenReturn(createCurrentUser());
        webClient = MockMvcWebClientBuilder.mockMvcSetup(mockMvc)
                .useMockMvcForHosts("pp.com").build();
    }

    private Page<Task> createEmptyPage() {
        Task task = new Task();
        task.setTitle("12");
        task.setUser(user);
        Sort sort = new Sort(Sort.Direction.ASC, "title");
        pageRequest = new PageRequest(PAGE_NUMBER, PAGE_SIZE, sort);
        Page<Task> emptyPage = new PageBuilder<Task>()
                .elements(Arrays.asList(task))
                .pageRequest(pageRequest)
                .totalElements(0)
                .build();
        return emptyPage;
    }

    private CurrentUser createCurrentUser() {
        CurrentUser currentUser = new CurrentUser(user);
        return currentUser;
    }

    @Test
    public void shouldBeErrorForUnathorizedUserForCallingTaskPage() throws Exception {
        mockMvc.perform(get("/tasks")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username="demo@localhost",roles={"USER","ADMIN"})
    public void shouldNotBeErrorsForAthorizedUserForCallingTaskPage() throws Exception {
        mockMvc.perform(get("/tasks")).andExpect(status().isOk());
    }

}
