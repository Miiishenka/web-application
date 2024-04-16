package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.form.UserRegisterCredentials;
import ru.itmo.wp.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByLoginAndPassword(String login, String password) {
        return login == null || password == null ? null : userRepository.findByLoginAndPassword(login, password);
    }

    public User findById(Long id) {
        return id == null ? null : userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAllByOrderByIdDesc();
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User register(UserRegisterCredentials userRegisterCredentials) {
        User user = new User();
        user.setLogin(userRegisterCredentials.getLogin());
        user.setName(userRegisterCredentials.getName());
        userRepository.save(user);
        userRepository.updatePasswordSha(user.getId(),
                userRegisterCredentials.getLogin(), userRegisterCredentials.getPassword());
        return user;
    }

    public void writePost(PostForm postForm, User user) {
        Post post = new Post();
        post.setTitle(postForm.getTitle());
        post.setText(postForm.getText());
        post.setUser(user);
        user.addPost(post);
        userRepository.save(user);
    }
}