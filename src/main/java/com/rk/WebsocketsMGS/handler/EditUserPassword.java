package com.rk.WebsocketsMGS.handler;

import com.google.gson.Gson;
import com.rk.WebsocketsMGS.domain.Operation;
import com.rk.WebsocketsMGS.domain.OperationType;
import com.rk.WebsocketsMGS.domain.User;
import com.rk.WebsocketsMGS.service.UserService;
import com.rk.WebsocketsMGS.timer.DeleteGeneratedData;
import com.rk.WebsocketsMGS.utils.Utils;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EditUserPassword implements Action {

    public static final Logger LOGGER = LoggerFactory.getLogger(EditUserPassword.class);

    private final String Id = "edit_user_password";

    final
    UserService service;

    public EditUserPassword(UserService service) {
        this.service = service;
    }

    @Override
    public TextMessage performAction(WebSocketSession session, Map<String, String> value) {

        //Client UI don't send new password

        /*String login = value.get("login");
        String password = Utils.getSHA256(value.get("password"));
        Optional<User> optionalUser = service.getByLogin(login);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (user.equals(new User(user.getId(), login, password))) {
                LOGGER.info("Password was changed");
                return new TextMessage(new Gson().toJson(new Operation(OperationType.auth, true)));
            }
        }*/
        return new TextMessage(new Gson().toJson(new Operation(OperationType.auth, false)));
    }

    @Override
    public String getId() {
        return Id;
    }

}
