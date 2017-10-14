package com.pp.dao;

import com.pp.repository.UserRepository;
import org.junit.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.Assert.assertThat;

/**
 * Created by astepanov
 */
public class TaskDaoTest extends AbstractDaoTest<UserRepository>{

    @Test
    @Sql("/one_user.sql")
    public void severalBooksForTheSameAuthorAreReturned() {
    }
}
