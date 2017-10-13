package com.pp.service;

import com.pp.domain.page.PageList;
import com.pp.domain.page.PageNumber;
import com.pp.domain.page.PageWrapper;
import com.pp.domain.task.Task;
import com.pp.domain.task.TaskCreateForm;
import com.pp.domain.user.User;
import com.pp.repository.TaskRepository;
import com.pp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public PageList<Task> getPageList(Optional<Integer> number, int buttonsAmount, Optional<Integer> pageSize) {
        final Page<Task> page = findAllPageable(new PageRequest((new PageNumber(number))
                .evalPageNumber(number), pageSize.orElse(buttonsAmount)));
        final PageWrapper<Task> pageWrapper = new PageWrapper<>(page);
        final PageList<Task> pageList = new PageList<>(pageWrapper, buttonsAmount);
        pageList.initializeBounds();
        return pageList;
    }
}
