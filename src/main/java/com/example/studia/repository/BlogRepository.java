package com.example.studia.repository;

import com.example.studia.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
//    Page<Blog> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
