package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Post;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class MemPostRepository {

    private static final AtomicInteger POST_ID = new AtomicInteger(0);
    private final Map<Integer, Post> posts = new HashMap<>();

    public MemPostRepository() {
        save(Post.of("Продаю машину", "Продаю машину ладу 01."));
    }

    public Collection<Post> getAllPosts() {
        return posts.values();
    }

    public Post getPostById(int id) {
        return posts.get(id);
    }

    public Post save(Post post) {
        Post rsl;
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
            rsl = add(post);
        } else {
            rsl = replace(post);
        }
        return rsl;
    }

    private Post add(Post post) {
        return posts.putIfAbsent(post.getId(), post);
    }

    private Post replace(Post post) {
        return posts.replace(post.getId(), post);
    }
}
