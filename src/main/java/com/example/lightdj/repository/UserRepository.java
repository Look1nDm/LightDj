package com.example.lightdj.repository;

import com.example.lightdj.domain.user.Role;
import com.example.lightdj.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    List<User> findByRoles(Role role);
    @Query(value = """
            select * from users where username like concat('%',:username,'%')
            """, nativeQuery = true)
    Optional<User> findUserByUsername(@Param("username") String username);
}
