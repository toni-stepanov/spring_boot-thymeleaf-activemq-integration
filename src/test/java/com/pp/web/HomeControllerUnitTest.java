package com.pp.web;

import com.pp.controller.HomeController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by astepanov
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeControllerUnitTest {

    private HomeController controller;

    private MockMvc mockMvc;

    @Before
    public void init() {
        controller = new HomeController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void mainRequestShouldReturnHomeUrl() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("home"));
    }

}
