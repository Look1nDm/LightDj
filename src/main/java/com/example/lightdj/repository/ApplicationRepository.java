package com.example.lightdj.repository;

import com.example.lightdj.domain.application.Application;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    @Query(value = """
            select * from applications where user_id = :id 
            order by date_created_application desc""", nativeQuery = true)
    List<Application> findAllByUserIdDesc(PageRequest pageRequest, @Param("id") Long id);
    @Query(value = """
            select * from applications where user_id = :id 
            order by date_created_application""", nativeQuery = true)
    List<Application> findAllByUserIdAsc(PageRequest pageRequest, @Param("id") Long id);
}
