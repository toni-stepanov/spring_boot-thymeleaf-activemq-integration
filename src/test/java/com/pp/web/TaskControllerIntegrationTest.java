package com.pp.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pp.common.PageBuilder;
import com.pp.controller.TaskController;
import com.pp.controller.advice.CurrentUserControllerAdvice;
import com.pp.domain.task.Task;
import com.pp.domain.task.TaskCreateForm;
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
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private final int PAGE_SIZE = 5;
    private Pageable pageRequest;
    private User user;

    @MockBean
    private TaskCreateFormValidator formValidator;

    @MockBean
    private CurrentUserControllerAdvice currentUserControllerAdvice;

    @MockBean
    private TaskService taskService;

    private TaskCreateForm createForm;

    @Before
    public void setUp() throws Exception {
        createForm = new TaskCreateForm("title", "dasc");
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity())
            .build();
        Task task = createTask();
        when(taskService.findAllPageable(any())).thenReturn((new PageBuilder<Task>())
                .createOneElemPage(task, PAGE_NUMBER, PAGE_SIZE));
        when(currentUserControllerAdvice.getCurrentUser(any())).thenReturn(createCurrentUser());
        webClient = MockMvcWebClientBuilder.mockMvcSetup(mockMvc)
                .useMockMvcForHosts("pp.com").build();
        doNothing().when(formValidator).validate(any(), any());
    }

    private Task createTask() {
        user = new User(1L, "test@test.com", "124214", Role.ADMIN, new ArrayList<Task>());
        Task task = new Task();
        task.setTitle("12");
        task.setUser(user);
        return task;
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

    @Test
    @WithMockUser(username="demo@localhost",roles={"USER","ADMIN"})
    public void taskPageShouldContentsRightTaskItems() throws Exception {
        HtmlPage page = webClient.getPage("http://pp.com/tasks");
        List<String> taskParam = page.getElementsByTagName("td")
                .stream().map(DomNode::asText).collect(toList());
        assertThat(taskParam.get(1), is("12"));
        assertThat(taskParam.get(2), is("test@test.com"));
    }

    @Test
    public void shouldBeErrorsForNonAthorizedUserForCallingCreateTask() throws Exception {
        mockMvc.perform(post("/task/create", createForm)).andExpect(status().isUnauthorized());
    }

}
