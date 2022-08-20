package com.rk.WebsocketsMGS.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Operation {

    private OperationType operation;

    private boolean status;

}
