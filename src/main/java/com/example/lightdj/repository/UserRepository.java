package com.example.lightdj.repository;

import com.example.lightdj.domain.application.Status;
import com.example.lightdj.domain.user.Role;
import com.example.lightdj.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(Long id);

    List<User> findByRoles(Role role);

    Optional<User> findUserByEmail(String email);
}
