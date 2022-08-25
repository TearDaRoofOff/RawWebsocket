package com.rk.WebsocketsMGS.handler;

import com.google.gson.Gson;
import com.rk.WebsocketsMGS.domain.OperationType;
import com.rk.WebsocketsMGS.domain.OperationWithData;
import com.rk.WebsocketsMGS.timer.SendEveryTenSeconds;
import com.rk.WebsocketsMGS.utils.Utils;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Component
public abstract class StartJob implements Action {

    public static final Logger LOGGER = LoggerFactory.getLogger(StartJob.class);

    private final String Id = "start_job";

    @Getter
    private final Map<WebSocketSession, SendEveryTenSeconds> timersMap = new ConcurrentHashMap<>();

    final
    Auth auth;

    public StartJob(Auth auth) {
        this.auth = auth;
    }

    @Override
    public TextMessage performAction(WebSocketSession session, Map<String, String> map) {
        LOGGER.info("START JOB");
        SendEveryTenSeconds timer = createTimer();
        try {
            timer.startTimer(session, auth.getGeneratedValue(), auth.getLoggedUsers().get(session));
            timersMap.put(session, timer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new TextMessage("");
    }

    @Override
    public String getId() {
        return Id;
    }

    @Lookup("myTimer")
    protected abstract SendEveryTenSeconds createTimer();
}
