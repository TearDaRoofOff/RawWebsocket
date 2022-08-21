package com.rk.WebsocketsMGS.handler;

import com.rk.WebsocketsMGS.service.UserService;
import com.rk.WebsocketsMGS.timer.SendEveryTenSeconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StopJob implements Action {

    public static final Logger LOGGER = LoggerFactory.getLogger(StopJob.class);

    private final String Id = "stop_job";

    private final Map<String, SendEveryTenSeconds> timersMap = new HashMap<>();

    @Override
    public TextMessage performAction(WebSocketSession session, Map<String, String> map) {
        LOGGER.info("Кол-во запущенных таймеров - {}", timersMap.size());
        return new TextMessage("");
    }

    @Override
    public String getId() {
        return Id;
    }
}
