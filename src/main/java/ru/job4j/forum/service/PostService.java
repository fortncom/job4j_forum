package ru.job4j.forum.service;

import org.springframework.stereotype.Service;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.repository.*;

import java.time.Instant;
import java.util.*;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorities;

    public PostService(PostRepository postRepository,
                       UserRepository userRepository, AuthorityRepository authorities) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.authorities = authorities;
    }

    public User findUserByName(String name) {
        return userRepository.findUserByUsername(name);
    }

    public Collection<Post> getAllPosts() {
        List<Post> rsl = new ArrayList<>();
        postRepository.findAll().forEach(rsl::add);
        return rsl;
    }

    public Post getPostById(int id) {
        return postRepository.findById(id).get();
    }

    public Collection<User> getAllUsers() {
        List<User> rsl = new ArrayList<>();
        userRepository.findAll().forEach(rsl::add);
        return rsl;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public void save(User user) throws UserConstraintViolation {
        userRepository.save(user);
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setCreated(Date.from(Instant.now()));

        } else {
            post.setCreated(postRepository.findById(post.getId()).get().getCreated());
        }
        postRepository.save(post);
    }
}