package ru.itmo.wp.form;

import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentForm {
    @NotBlank
    @Size(min = 1, max = 10000)
    @Lob
    private String text;
    @NotNull
    private String jwt;
    @NotNull
    private Post post;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
