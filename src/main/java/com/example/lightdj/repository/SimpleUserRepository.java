package com.example.lightdj.repository;

import com.example.lightdj.domain.Role;
import com.example.lightdj.domain.user.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long> {
    Optional<SimpleUser> findUserByEmail(String email);

    List<SimpleUser> findByRoles(Role role);
    @Query(value = """
            select * from users where username like concat('%',:username,'%')
            """, nativeQuery = true)
    Optional<SimpleUser> findUserByUsername(@Param("username") String username);
}
