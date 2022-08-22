package com.rk.WebsocketsMGS.service;

import com.rk.WebsocketsMGS.domain.User;
import com.rk.WebsocketsMGS.dto.UserTO;
import com.rk.WebsocketsMGS.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userService")
public class UserService  {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public User get(int id) {
        return repository.get(id);
    }

    public Optional<User> getByLogin(String login){
        return getAll().stream().filter(user -> login.equals(user.getName())).findFirst();
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public List<UserTO> getAllDTO() {
        return getAll().stream().map(UserTO::new).toList();
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(user);
    }

    public void enable(int id) {
        User user = get(id);
        if (user.getEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
    }
}