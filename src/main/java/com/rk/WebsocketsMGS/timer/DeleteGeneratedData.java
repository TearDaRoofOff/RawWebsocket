package com.rk.WebsocketsMGS.timer;

import com.rk.WebsocketsMGS.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class DeleteGeneratedData extends TimerTask {

    private final User user;
    private final Map<String, List<Integer>> generatedValue;

    public DeleteGeneratedData(User user, Map<String, List<Integer>> generatedValue) {
        this.user = user;
        this.generatedValue = generatedValue;
    }

    public static final Logger LOGGER = LoggerFactory.getLogger(DeleteGeneratedData.class);

    @Override
    public void run() {
        LOGGER.info("Data {} for user {} completed.", generatedValue.remove(user.getName()), user.getName());
    }
}
