package com.pp.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by astepanov
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TaskControllerSystemTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

    @Test
    public void tasksPageShouldNotBeAvailableForNonAuthorizedUsers() throws Exception {
        mockMvc.perform(get("/tasks").accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isNonAuthoritativeInformation());
    }
}
