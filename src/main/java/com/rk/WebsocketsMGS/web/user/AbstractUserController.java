package com.rk.WebsocketsMGS.web.user;

import com.rk.WebsocketsMGS.domain.User;
import com.rk.WebsocketsMGS.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractUserController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    public List<User> getAll() {
                return service.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public User create(User user) {
        log.info("create {}", user);
//        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
//        assureIdConsistent(user, id);
        service.update(user);
    }

    public void enable(int id) {
//        log.info(enabled ? "enable {}" : "disable {}", id);
        service.enable(id);
    }
}