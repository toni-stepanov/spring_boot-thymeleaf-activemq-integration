package com.pp.service;

import com.pp.domain.page.PageList;
import com.pp.domain.page.PageWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

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
