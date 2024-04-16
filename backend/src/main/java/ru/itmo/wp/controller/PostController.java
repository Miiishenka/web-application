package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.form.validator.WriteCommentValidator;
import ru.itmo.wp.form.validator.WritePostValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.PostService;

import javax.validation.Valid;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/1")
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final JwtService jwtService;
    private final WritePostValidator writePostValidator;
    private final WriteCommentValidator writeCommentValidator;

    public PostController(PostService postService, UserService userService, JwtService jwtService, WritePostValidator writePostValidator, WriteCommentValidator writeCommentValidator) {
        this.postService = postService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.writePostValidator = writePostValidator;
        this.writeCommentValidator = writeCommentValidator;
    }

    @InitBinder("userRegisterCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(writePostValidator, writeCommentValidator);
    }
    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }

    @PostMapping("posts")
    public void writePost(@RequestBody @Valid PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        userService.writePost(postForm, jwtService.find(postForm.getJwt()));
    }

    @PostMapping("comments")
    public void writeComment(@RequestBody @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        postService.writeComment(commentForm, jwtService.find(commentForm.getJwt()));
    }
}
