package com.pp.dao;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.pp.domain.task.Task;
import com.pp.domain.user.Role;
import com.pp.domain.user.User;
import com.pp.repository.TaskRepository;
import org.junit.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
/**
 * Created by astepanov
 */
public class TaskDaoTest extends AbstractDaoTest<TaskRepository>{

    @Test
    @Sql("/one_task.sql")
    public void testInitialSettings() {
        assertThat((List<Task>) dao.findAll(), not(empty()));
    }

    @Test
    @DataSet(value = "empty.xml", disableConstraints = true)
    @ExpectedDataSet("default-tasks.xml")
    @Commit
    public void shouldSaveTasksProperly(){
        User user = new User(1L, "test@test.com", "124214", Role.ADMIN, new ArrayList<Task>());
        dao.save(new Task(1L, "check test", "do it quickly", user));
    }

}
