package com.rk.WebsocketsMGS.config;

import com.rk.WebsocketsMGS.handler.SocketHandlers;
import com.rk.WebsocketsMGS.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    public static final Logger LOGGER = LoggerFactory.getLogger(WebSocketConfig.class);

    @Autowired
    UserService userService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketHandlers(userService), "/api/test");
    }

    @PostConstruct
    public void init(){
        LOGGER.info("*******WebSocketConfig class was created**********");
    }
}
