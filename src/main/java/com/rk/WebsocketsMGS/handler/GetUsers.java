package com.rk.WebsocketsMGS.handler;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GetUsers implements Action{

    private final String Id = "get_users_list";

    @Override
    public String performAction(Map map) {
        return "get user operation";
    }

    @Override
    public String getId() {
        return Id;
    }
}
