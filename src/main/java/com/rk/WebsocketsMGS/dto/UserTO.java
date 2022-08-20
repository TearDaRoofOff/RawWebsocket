package com.rk.WebsocketsMGS.dto;

import com.rk.WebsocketsMGS.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserTO {

    private int user_id;

    private String login;

    private Date last_auth;

    private boolean status;

    public UserTO(int user_id, String login, Date last_auth, boolean status) {
        this.user_id = user_id;
        this.login = login;
        this.last_auth = last_auth;
        this.status = status;
    }

    public UserTO(User user) {
        this.user_id = user.getId();
        this.login = user.getName();
        this.last_auth = user.getLast_auth();
        this.status = user.getEnabled();
    };
}
