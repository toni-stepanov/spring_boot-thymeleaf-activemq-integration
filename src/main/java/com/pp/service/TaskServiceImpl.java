package com.pp.service;

import com.pp.domain.task.Task;
import com.pp.domain.task.TaskCreateForm;
import com.pp.domain.user.User;
import com.pp.repository.TaskRepository;
import com.pp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by astepanov
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Task> findAllPageable(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Task create(TaskCreateForm form) {
        Task task = new Task();
        task.setDescription(form.getDescription());
        task.setTitle(form.getTitle());
        // TODO: temporary set admin for user
        User firstUser = userRepository.findOne((long) 1);
        task.setUser(firstUser);
        return taskRepository.save(task);
    }
}
