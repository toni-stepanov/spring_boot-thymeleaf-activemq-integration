package com.pp.domain;

import com.pp.common.PageBuilder;
import com.pp.controller.advice.CurrentUserControllerAdvice;
import com.pp.domain.page.PageList;
import com.pp.domain.page.PageWrapper;
import com.pp.domain.task.Task;
import com.pp.domain.task.TaskCreateForm;
import com.pp.domain.user.Role;
import com.pp.domain.user.User;
import com.pp.domain.validator.TaskCreateFormValidator;
import com.pp.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by astepanov
 */
@RunWith(MockitoJUnitRunner.class)
public class PageListUnitTest {

    @Mock
    private Page page;

    @Test
    public void shouldShowAllPagesForSmallAmountOfTasks() throws Exception {
        when(page.getTotalElements()).thenReturn((long) 10);
        when(page.getTotalPages()).thenReturn(2);
        PageWrapper pageWrapper = new PageWrapper(page);
        PageList pageList = new PageList(pageWrapper, 5);
        pageList.initializeBounds();
        assertThat(pageList.getStart(), is(1));
        assertThat(pageList.getEnd(), is(2));
    }

    @Test
    public void shouldShowCorrectEndAndStartPages() throws Exception {
        when(page.getTotalElements()).thenReturn((long) 10);
        when(page.getTotalPages()).thenReturn(20);
        PageWrapper pageWrapper = new PageWrapper(page);
        PageList pageList = new PageList(pageWrapper, 3);
        pageList.initializeBounds();
        assertThat(pageList.getStart(), is(1));
        assertThat(pageList.getEnd(), is(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForOddAmoutOfButtons() throws Exception {
        when(page.getTotalElements()).thenReturn((long) 10);
        when(page.getTotalPages()).thenReturn(10);
        PageWrapper pageWrapper = new PageWrapper(page);
        PageList pageList = new PageList(pageWrapper, 6);
        pageList.initializeBounds();
    }

}
