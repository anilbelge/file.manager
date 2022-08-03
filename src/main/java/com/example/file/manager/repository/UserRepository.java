package com.example.file.manager.repository;

import com.example.file.manager.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<AppUser, Integer> {

    boolean existsByUsername(String username);

    AppUser findByUsername(String username);
}
