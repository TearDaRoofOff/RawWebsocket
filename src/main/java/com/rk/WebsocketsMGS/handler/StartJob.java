package com.rk.WebsocketsMGS.handler;

import com.google.gson.Gson;
import com.rk.WebsocketsMGS.domain.OperationType;
import com.rk.WebsocketsMGS.domain.OperationWithData;
import com.rk.WebsocketsMGS.timer.SendEveryTenSeconds;
import com.rk.WebsocketsMGS.timer.SheduledEvent;
import com.rk.WebsocketsMGS.utils.Utils;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Component
public class StartJob implements Action {

    public static final Logger LOGGER = LoggerFactory.getLogger(StartJob.class);

    private final String Id = "start_job";

    /*    @Autowired
        SendEveryTenSeconds sendEveryTenSeconds;*/
    @Getter
    private final Map<WebSocketSession, SheduledEvent> mapOfTimer = new ConcurrentHashMap<>();

    final
    Auth auth;

    public StartJob(Auth auth) {
        this.auth = auth;
    }

    @Override
    public TextMessage performAction(WebSocketSession session, Map<String, String> map) {
        LOGGER.info("START JOB");
        try {
            SendEveryTenSeconds sendEveryTenSeconds = new SendEveryTenSeconds();
            sendEveryTenSeconds.startTimer(session, auth.getGeneratedValue(), auth.getLoggedUsers().get(session));
            mapOfTimer.put(session, sendEveryTenSeconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new TextMessage("");
    }

    @Override
    public String getId() {
        return Id;
    }
}
