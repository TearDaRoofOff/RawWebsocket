package com.rk.WebsocketsMGS.timer;

import com.google.gson.Gson;
import com.rk.WebsocketsMGS.domain.OperationType;
import com.rk.WebsocketsMGS.domain.OperationWithData;
import com.rk.WebsocketsMGS.domain.User;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Component
//@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SendEveryTenSeconds implements SheduledEvent {

    public static final Logger LOGGER = LoggerFactory.getLogger(SendEveryTenSeconds.class);

    @Getter
    private String uuid;

    private Timer timer;

    public void startTimer(WebSocketSession session, Map<String, List<Integer>> map, User user) throws InterruptedException {
        uuid = session.getId();
        LOGGER.info("Timer id is {}", uuid);
        TimerTask task = new TimerTask() {
            @SneakyThrows
            public void run() {
                List<Integer> integerList = map.get(user.getName());
                if (integerList == null) cancel();

                String payload = getPayload(convertListToArr(integerList));
                session.sendMessage(new TextMessage(payload));
                LOGGER.info("Было отправлено {}", payload);
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 0L, 10000L);
    }

    private String getPayload(int[][] ints) {
        return new Gson().toJson(new OperationWithData(OperationType.new_result, Arrays.stream(ints).toList()));
    }

    public void stopTimer() {
        timer.cancel();
    }

    private int[][] convertListToArr(List<Integer> arr) {
        ArrayList<Integer> list = new ArrayList<>(arr);
        Collections.shuffle(list);
        int[][] array = new int[4][5];
        final AtomicInteger integer = new AtomicInteger(0);
        IntStream.range(0, array.length).forEach(x ->
                IntStream.range(0, array[x].length).forEach(y ->
                        array[x][y] = list.get(integer.incrementAndGet())));
        return array;
    }
}
