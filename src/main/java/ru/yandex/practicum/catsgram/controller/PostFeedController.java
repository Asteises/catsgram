package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.catsgram.exception.IncorrectParameterException;
import ru.yandex.practicum.catsgram.model.FeedParams;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.catsgram.Constants.SORTS;

@RestController()
@RequestMapping("/feed/friends")
public class PostFeedController {

    private final PostService postService;

    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    List<Post> getFriendsFeed(@RequestBody FeedParams feedParams) {
        if (!SORTS.contains(feedParams.getSort())) {
            throw new IncorrectParameterException("sort");
        }
        if (feedParams.getSize() == null || feedParams.getSize() <= 0) {
            throw new IncorrectParameterException("size");
        }
        if (feedParams.getFriendsEmails().isEmpty()) {
            throw new IncorrectParameterException("friendsEmails");
        }

        List<Post> result = new ArrayList<>();
        for (String friendEmail : feedParams.getFriendsEmails()) {
            result.addAll(postService.findAllByUserEmail(friendEmail, feedParams.getSize(), feedParams.getSort()));
        }
        return result;
    }
}