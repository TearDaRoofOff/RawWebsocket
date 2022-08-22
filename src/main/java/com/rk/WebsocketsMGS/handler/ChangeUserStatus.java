package com.rk.WebsocketsMGS.handler;

import com.google.gson.Gson;
import com.rk.WebsocketsMGS.domain.Operation;
import com.rk.WebsocketsMGS.domain.OperationType;
import com.rk.WebsocketsMGS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

@Component
public class ChangeUserStatus implements Action{

    private final String Id = "change_user_status";

    final
    UserService service;

    public ChangeUserStatus(UserService service) {
        this.service = service;
    }

    @Override
    public TextMessage performAction(WebSocketSession session, Map<String, String> value) {
        service.enable(Integer.parseInt(value.get("user_id")));
        return new TextMessage(new Gson().toJson(new Operation(OperationType.change_user_status, true)));
    }

    @Override
    public String getId() {
        return Id;
    }
}
