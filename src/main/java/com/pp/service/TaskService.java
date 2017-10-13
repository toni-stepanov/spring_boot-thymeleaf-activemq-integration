package com.pp.service;

import com.pp.domain.page.PageList;
import com.pp.domain.task.Task;
import com.pp.domain.task.TaskCreateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Created by astepanov
 */
public interface TaskService {

    Page<Task> findAllPageable(Pageable pageable);

    Task create(TaskCreateForm form);

    PageList<Task> getPageList(Optional<Integer> number, int buttonsAmount, Optional<Integer> pageSize);
}
