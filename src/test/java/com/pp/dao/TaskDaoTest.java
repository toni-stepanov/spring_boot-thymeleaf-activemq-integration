package com.pp.dao;

import com.pp.repository.TaskRepository;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.google.common.collect.ImmutableMap;
import com.pp.repository.UserRepository;
import org.junit.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by astepanov
 */
public class TaskDaoTest extends AbstractDaoTest<UserRepository>{

    @Test
    @Sql("user.sql")
    public void ifNoTasksEmptyListShouldBeReturned(){
        assertThat(dao.findAll(), is(empty()));
    }


}
