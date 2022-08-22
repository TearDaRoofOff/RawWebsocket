package com.rk.WebsocketsMGS.handler;

import com.rk.WebsocketsMGS.timer.SendEveryTenSeconds;
import com.rk.WebsocketsMGS.timer.SheduledEvent;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class StopJob implements Action {

    public static final Logger LOGGER = LoggerFactory.getLogger(StopJob.class);

    private final String Id = "stop_job";

    final
    StartJob startJob;

    public StopJob(StartJob startJob) {
        this.startJob = startJob;
    }

    @Override
    public TextMessage performAction(WebSocketSession session, Map<String, String> map) {
        SheduledEvent sheduledEvent = startJob.getMapOfTimer().get(session);
        sheduledEvent.stopTimer();
        LOGGER.info("Timer with id {} was stoped", sheduledEvent.getUuid());
        return new TextMessage("");
    }

    @Override
    public String getId() {
        return Id;
    }
}
