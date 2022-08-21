package com.rk.WebsocketsMGS.handler;

import lombok.Getter;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class MethodHandlers {

    @Getter
    private final Map<String, Action> handlersMap = new HashMap<>();

    @Autowired
    public void ActionFactory(ListableBeanFactory beanFactory) {
        Collection<Action> interfaces = beanFactory.getBeansOfType(Action.class).values();
        interfaces.forEach(filter -> handlersMap.put(filter.getId(), filter));
    }

    public int getSize() {
        return handlersMap.size();
    }

}
