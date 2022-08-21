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
public class Auth implements Action {

    public static final Logger LOGGER = LoggerFactory.getLogger(Auth.class);

    private final String Id = "auth";

    @Getter
    private final Map<String, List<Integer>> generatedValue = new ConcurrentHashMap<>();

    @Getter
    private final Map<WebSocketSession, User> loggedUsers = new ConcurrentHashMap<>();

    @Autowired
    UserService service;

    @Override
    public TextMessage performAction(WebSocketSession session, Map<String, String> value) {
        String login = value.get("login");
        String password = Utils.getSHA256(value.get("password"));
        Optional<User> optionalUser = service.getByLogin(login);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            if (user.equals(new User(user.getId(), login, password))) {
                //TODO Связать юзера с сессией ??
                loggedUsers.put(session, user);
                user.setLast_auth(new Date());
                generatedValue.put(user.getName(), Utils.createPrimesArray(100));
                startTimer(user);
                LOGGER.info("User {} was authenticated!", login);
                return new TextMessage(new Gson().toJson(new Operation(OperationType.auth, true)));
            }
        }
        return new TextMessage(new Gson().toJson(new Operation(OperationType.auth, false)));
    }

    @Override
    public String getId() {
        return Id;
    }

    //todo перенести в DeleteGeneratedData
    private void startTimer(User user) {
        LocalDateTime fiveMinutesLater = LocalDateTime.now().plusMinutes(5);
        Date fiveMinutesLaterAsDate = Date.from(fiveMinutesLater.atZone(ZoneId.systemDefault()).toInstant());
        new Timer().schedule(new DeleteGeneratedData(user, generatedValue), fiveMinutesLaterAsDate);
    }

}
