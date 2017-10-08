package com.pp.web;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pp.controller.TaskController;
import com.pp.domain.validator.TaskCreateFormValidator;
import com.pp.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskCreateFormValidator formValidator;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity())
            .build();
        webClient = MockMvcWebClientBuilder.mockMvcSetup(mockMvc)
                .useMockMvcForHosts("pp.com").build();
    }

    @Test
    public void shouldBeErrorForUnathorizedUserForCallingTaskPage() throws Exception {
        mockMvc.perform(get("/tasks")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void shouldNotBeErrorsForAthorizedUserForCallingTaskPage() throws Exception {
        mockMvc.perform(get("/tasks")).andExpect(status().isOk());
    }

}
