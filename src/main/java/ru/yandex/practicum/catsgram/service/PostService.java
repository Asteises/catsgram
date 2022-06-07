package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.catsgram.controller.PostController;
import ru.yandex.practicum.catsgram.exeptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;

    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(Integer size, Integer from, String sort) {
        return posts.stream().sorted((p0, p1) -> {
            int comp = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
            if(sort.equals("desc")){
                comp = -1 * comp; //обратный порядок сортировки
            }
            return comp;
        }).skip(from).limit(size).collect(Collectors.toList());
    }


    public Optional<Post> findById(int postId) {
        log.debug("Текущий пост: {}", postId);
        return posts.stream().filter(x -> x.getId() == postId).findFirst();
    }

    public Post create(Post post) throws UserNotFoundException {
        User postAuthor = userService.findByEmail(post.getAuthor());
        if (postAuthor == null) {
            throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден");
        }
        log.debug("Сохраняем post: {}", post.toString());
        posts.add(post);
        return post;
    }
}
