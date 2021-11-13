package ru.job4j.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.service.PostService;

@Controller
public class PostControl {

    private final PostService service;

    public PostControl(PostService posts) {
        this.service = posts;
    }

    @GetMapping({"/post"})
    public String index(Model model, @RequestParam(name = "id") Integer id) {
        model.addAttribute("post", service.getPostById(id));
        return "post";
    }

    @GetMapping(path = "/create")
    public String create(Model model) {
        return "create";
    }

    @GetMapping(path = "/edit")
    public String edit(Model model, @RequestParam(name = "id") Integer id) {
        model.addAttribute("post", service.getPostById(id));
        return "edit";
    }

    @PostMapping(path = "/save")
    public String save(@ModelAttribute Post post) {
        service.save(post);
        return "redirect:/";
    }
}
