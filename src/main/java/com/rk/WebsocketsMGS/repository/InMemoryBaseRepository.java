package com.rk.WebsocketsMGS.repository;

import com.rk.WebsocketsMGS.domain.AbstractBaseEntity;
import com.rk.WebsocketsMGS.domain.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.rk.WebsocketsMGS.domain.User.START_SEQ;

public class InMemoryBaseRepository<T extends AbstractBaseEntity>{

    static final AtomicInteger counter = new AtomicInteger(START_SEQ);

    final Map<Integer, T> map = new ConcurrentHashMap<>();

    public T save(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        entity.setId(counter.incrementAndGet());
        map.put(entity.getId(), entity);
        return entity;
    }

    public boolean delete(int id) {
        return map.remove(id) != null;
    }

    public T get(int id) {
        return map.get(id);
    }

    public List<User> getAll() {
        List<User> users = map.values().stream().map(t -> (User) t).toList();
        return users;
    }
}
