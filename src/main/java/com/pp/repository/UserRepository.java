package com.pp.repository;

import com.pp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Created by astepanov
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneById(long id);
}
