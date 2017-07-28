package com.pp.repository;

import com.pp.domain.task.Task;
import org.springframework.data.repository.PagingAndSortingRepository;


/**
 * Created by astepanov
 */
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
}
