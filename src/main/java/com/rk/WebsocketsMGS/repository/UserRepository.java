package com.rk.WebsocketsMGS.repository;

import com.rk.WebsocketsMGS.domain.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    boolean delete(int id);

    User get(int id);

    List<User> getAll();
}
