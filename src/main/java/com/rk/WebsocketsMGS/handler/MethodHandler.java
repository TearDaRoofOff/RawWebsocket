package com.rk.WebsocketsMGS.handler;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class MethodHandler {

    private Map<String, Action> factory = new HashMap<>();

    @Autowired
    public void ReportFactory(ListableBeanFactory beanFactory) {
        Collection<Action> interfaces = beanFactory.getBeansOfType(Action.class).values();
        interfaces.forEach(filter -> factory.put(filter.getId(), filter));
    }

    public int getSize() {
        return factory.size();
    }

}
