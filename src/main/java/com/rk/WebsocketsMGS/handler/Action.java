package com.rk.WebsocketsMGS.handler;

import java.util.Map;

interface Action {
    String performAction(Map map);

    String getId();
}
