package com.rk.WebsocketsMGS.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class OperationWithData {

    private OperationType operation;

    private List data;

}
