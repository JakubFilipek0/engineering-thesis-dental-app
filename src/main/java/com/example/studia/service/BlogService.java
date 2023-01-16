package com.example.studia.service;

import com.example.studia.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface BlogService {
    Blog setBlog(Blog blog);
    void deleteBlog(Long blogId);
    Blog getBlog(Long blogId);
    List<Blog> getBlogs();

    Page<Blog> findByName(String name, Pageable pageable);
    Page<Blog> getBlogs(Pageable pageable);
    Page<Blog> findPaginated(int pageNumber);
}
