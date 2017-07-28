package com.pp.service;

import com.pp.domain.user.User;
import com.pp.domain.user.UserCreateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Created by astepanov
 */
public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Page<User> findAllPageable(Pageable pageable);

    User create(UserCreateForm form);

    User save(User user);

}
