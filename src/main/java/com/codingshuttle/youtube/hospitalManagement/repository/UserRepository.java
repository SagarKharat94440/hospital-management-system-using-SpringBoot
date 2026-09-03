package com.codingshuttle.youtube.hospitalManagement.repository;

import com.codingshuttle.youtube.hospitalManagement.entity.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
   Optional<User>  findByUsername(String username);

    User save(User build);
}