package ru.job4j.forum.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.UserConstraintViolation;
import ru.job4j.forum.service.PostService;

@Controller
public class RegControl {

    private final PostService postService;
    private final PasswordEncoder encoder;

    public RegControl(PostService postService, PasswordEncoder encoder) {
        this.postService = postService;
        this.encoder = encoder;
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        String rsl = "redirect:/login";
        try {
            user.setEnabled(true);
            postService.save(user);
        } catch (UserConstraintViolation e) {
            model.addAttribute("errorMessage", e.getMessage());
            rsl = "reg";
        }
        return rsl;
    }
}
