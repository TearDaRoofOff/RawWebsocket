package com.rk.WebsocketsMGS.handler;

import com.google.gson.Gson;
import com.rk.WebsocketsMGS.domain.Operation;
import com.rk.WebsocketsMGS.domain.OperationType;
import com.rk.WebsocketsMGS.domain.User;
import com.rk.WebsocketsMGS.service.UserService;
import com.rk.WebsocketsMGS.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

import static com.rk.WebsocketsMGS.domain.AbstractBaseEntity.START_SEQ;

@Component
public class AddUser implements Action {

    public static final Logger LOGGER = LoggerFactory.getLogger(AddUser.class);

    private final String Id = "add_user";

    @Autowired
    UserService service;

    @Override
    public TextMessage performAction(WebSocketSession session, Map<String, String> value) {
        String login = value.get("login");
        String password = Utils.getSHA256(value.get("password"));

        LOGGER.info("generated password - {}", password);

        User user = service.create(new User(START_SEQ, login, password));

        LOGGER.info("User with id={}, login={} and password={} was added!", user.getId(), user.getName(), user.getPassword());

        return new TextMessage(new Gson().toJson(new Operation(OperationType.add_user, true)));
    }

    @Override
    public String getId() {
        return Id;
    }
}
