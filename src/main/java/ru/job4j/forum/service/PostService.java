package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.MemPostRepository;
import ru.job4j.forum.repository.MemUserRepository;
import ru.job4j.forum.repository.UserConstraintViolation;

import java.util.*;

@Service
public class PostService {

    private final MemPostRepository postRepository;
    private final MemUserRepository userRepository;

    public PostService(
            MemPostRepository postRepository, MemUserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public User findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public Collection<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }

    public Post getPostById(int id) {
        return postRepository.getPostById(id);
    }

    public Collection<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public void save(User user) throws UserConstraintViolation {
        userRepository.save(user);
    }

    public void save(Post post) {
        postRepository.save(post);
    }
}