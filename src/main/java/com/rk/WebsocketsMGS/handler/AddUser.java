package com.rk.WebsocketsMGS.handler;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AddUser implements Action{

    private final String Id = "add_user";

    @Override
    public String performAction(Map map) {
        return "add_user operation";
    }

    @Override
    public String getId() {
        return Id;
    }
}
