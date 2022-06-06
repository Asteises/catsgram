package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.controller.PostController;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final List<Post> posts = new ArrayList<>();

    public List<Post> findAll() {
        log.debug("Текущее количество постов: {}", posts.size());
        return posts;
    }

    public Post create(Post post) {
        log.debug("Сохраняем post: {}", post.toString());
        posts.add(post);
        return post;
    }
}
