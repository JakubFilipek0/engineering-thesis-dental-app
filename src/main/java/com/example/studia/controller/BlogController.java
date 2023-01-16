package com.example.studia.controller;

import com.example.studia.model.Blog;
import com.example.studia.security.CurrentUser;
import com.example.studia.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(value = "/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    CurrentUser currentUser;

    @GetMapping()
    public String blogPage(Model model) {

        List<Blog> blogs = blogService.getBlogs();
        model.addAttribute("blogs", blogs);
        model.addAttribute("number", 1);

        return getOnePage(model, 1);
    }

    @GetMapping("/{pageNumber}")
    public String getOnePage(Model model, @PathVariable(name = "pageNumber") int currentPage) {
        Page<Blog> page = blogService.findPaginated(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Blog> blogs = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("blogs", blogs);
        //System.out.println("USER: " + currentUser.getCurrentUserId());
        if (currentUser.getCurrentUser() != null) {
            model.addAttribute("userId", currentUser.getCurrentUserId());
        }

        return "blog/blogPage.html";
    }

    @GetMapping(value = "/addBlog")
    public String addBlog(Model model) {
        model.addAttribute("blog", new Blog());
        model.addAttribute("userId", currentUser.getCurrentUserId());
        return "blog/addBlog.html";
    }

    @PostMapping(value = "/addBlog/save")
    public String saveBlog(Blog blog, @RequestParam(name = "image") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (!file.isEmpty()) {
            String path = System.getProperty("user.dir") + "/uploads" + "/" + fileName;
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path)));
            stream.write(file.getBytes());
            stream.close();
            blog.setTitleImage(fileName);
        }

        blog.setDentist(currentUser.getCurrentUser());
        blog.setDateAdd(LocalDate.now());
        blogService.setBlog(blog);
        return "redirect:/redirect";
    }

    @GetMapping(value = "/delete/{blogId}")
    public String deleteBlog(@PathVariable(value = "blogId") Long blogId) {
        blogService.deleteBlog(blogId);
        return "/blog";
    }
}
