package com.rk.WebsocketsMGS.handler;

import com.google.gson.Gson;
import com.rk.WebsocketsMGS.domain.OperationType;
import com.rk.WebsocketsMGS.domain.OperationWithData;
import com.rk.WebsocketsMGS.dto.UserTO;
import com.rk.WebsocketsMGS.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;

@Component
public class GetUsers implements Action{

    public static final Logger LOGGER = LoggerFactory.getLogger(GetUsers.class);

    private final String Id = "get_user_list";

    final
    UserService service;

    public GetUsers(UserService service) {
        this.service = service;
    }

    @Override
    public TextMessage performAction(WebSocketSession session, Map<String, String> map) {
        List<UserTO> all = service.getAllDTO();
        String s = new Gson().toJson(all);
        return new TextMessage(new Gson().toJson(new OperationWithData(OperationType.get_user_list, all)));
    }

    @Override
    public String getId() {
        return Id;
    }
}
