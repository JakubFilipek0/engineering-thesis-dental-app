package com.example.studia.service;

import com.example.studia.model.Blog;
import com.example.studia.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog setBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public void deleteBlog(Long blogId) {
        blogRepository.deleteById(blogId);
    }

    @Override
    public Blog getBlog(Long blogId) {
        return blogRepository.findById(blogId).get();
    }

    @Override
    public List<Blog> getBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public Page<Blog> findByName(String name, Pageable pageable) {
//        return blogRepository.findByNameContainingIgnoreCase(name, pageable);
        return null;
    }

    @Override
    public Page<Blog> getBlogs(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> findPaginated(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1,4);
        return blogRepository.findAll(pageable);
    }
}
