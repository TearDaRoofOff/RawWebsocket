package com.rk.WebsocketsMGS.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

interface Action {
    TextMessage performAction(WebSocketSession session, Map<String, String> map);

    String getId();
}
