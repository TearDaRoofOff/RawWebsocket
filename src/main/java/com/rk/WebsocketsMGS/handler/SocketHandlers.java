package com.rk.WebsocketsMGS.handler;

import com.google.gson.Gson;
import com.rk.WebsocketsMGS.domain.Operation;
import com.rk.WebsocketsMGS.domain.OperationType;
import com.rk.WebsocketsMGS.domain.OperationWithData;
import com.rk.WebsocketsMGS.domain.User;
import com.rk.WebsocketsMGS.dto.UserTO;
import com.rk.WebsocketsMGS.service.UserService;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.rk.WebsocketsMGS.domain.AbstractBaseEntity.START_SEQ;

@Controller
public class SocketHandlers extends TextWebSocketHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(SocketHandlers.class);

    private final UserService service;

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Autowired
    MethodHandler methodHandler;

    public SocketHandlers(UserService service) {
        this.service = service;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("------Userservice was injected-------");
        LOGGER.info("Размер фабрики - {}.", methodHandler.getSize());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        var value = new Gson().fromJson(message.getPayload(), Map.class);
                /*for(WebSocketSession webSocketSession : sessions) {
            Map value = new Gson().fromJson(message.getPayload(), Map.class);
            webSocketSession.sendMessage(new TextMessage("Hello " + value.get("name") + " !"));
        }*/
        switch ((String) value.get("operation")) {
            case "add_user" -> {
                User user = service.create(new User(START_SEQ, (String) value.get("login"), (String) value.get("password")));
                LOGGER.info("User with id={} was added!", service.get(user.getId()));
                session.sendMessage(new TextMessage(new Gson().toJson(new Operation(OperationType.add_user, true))));
            }
            case "get_user_list" -> {
                List<UserTO> all = service.getAllDTO();
                String s = new Gson().toJson(all);
                session.sendMessage(new TextMessage(new Gson().toJson(new OperationWithData(OperationType.get_user_list, all))));
            }
            case "change_user_status" -> {
                service.enable(Integer.parseInt((String) value.get("user_id")));
                session.sendMessage(new TextMessage(new Gson().toJson(new Operation(OperationType.change_user_status, true))));
            }
        }
//        session.sendMessage(new TextMessage(new Gson().toJson(new Operation(OperationType.change_user_status, true))));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Bing bong Bing. We have a new connection! Session id={}", session.getId());
        //the messages will be broadcasted to all users.
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOGGER.info("Session id={} was closed.", session.getId());
        sessions.remove(session);
    }
}
