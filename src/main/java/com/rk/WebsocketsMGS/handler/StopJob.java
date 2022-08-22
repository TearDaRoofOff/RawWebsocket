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
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StopJob implements Action {

    public static final Logger LOGGER = LoggerFactory.getLogger(StopJob.class);

    private final String Id = "stop_job";
    @Getter
    private List<SendEveryTenSeconds> timersList = new CopyOnWriteArrayList<>();

    @Autowired
    public void ActionFactory(ListableBeanFactory beanFactory) {
        Collection<SendEveryTenSeconds> interfaces = beanFactory.getBeansOfType(SendEveryTenSeconds.class).values();
        timersList.addAll(interfaces);
    }

    public StopJob(List<SendEveryTenSeconds> timersList) {
        this.timersList = timersList;
    }

    @Override
    public TextMessage performAction(WebSocketSession session, Map<String, String> map) {
        timersList.forEach(System.out::println);
        LOGGER.info("Кол-во запущенных таймеров - {}", timersList.size());
        SendEveryTenSeconds sendEveryTenSeconds = timersList.get(0);
/*        timersList.stream()
                .filter(sendEveryTenSeconds -> sendEveryTenSeconds.getUuid().equals(session.getId()))
                .findFirst()
                .get()
                .stopTimer();*/
        return new TextMessage("");
    }

    @Override
    public String getId() {
        return Id;
    }
}
