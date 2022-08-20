package com.rk.WebsocketsMGS.handler;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Auth implements Action{

    private final String Id = "auth";

    @Override
    public String performAction(Map map) {
        return "auth operation";
    }

    @Override
    public String getId() {
        return Id;
    }
}
