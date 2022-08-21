package com.rk.WebsocketsMGS.handler;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class SocketHandlers extends TextWebSocketHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(SocketHandlers.class);

    final
    MethodHandlers methodHandler;

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public SocketHandlers(MethodHandlers methodHandler) {
        this.methodHandler = methodHandler;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        var value = new Gson().fromJson(message.getPayload(), Map.class);

        String action = (String) value.get("operation");
        TextMessage textMessage = methodHandler.getHandlersMap().get(action).performAction(session, value);
        session.sendMessage(textMessage);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("Bing bong Bing. We have a new connection! Session id={}", session.getId());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOGGER.info("Session id={} was closed.", session.getId());
        sessions.remove(session);
    }
}
