package com.rk.WebsocketsMGS.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.EnumSet;

@Setter
@Getter
public class User extends AbstractNamedEntity {

    private String password;

    private Date last_auth;

    private Boolean enabled;


    public User(Integer id, String name, String password) {
        super(id, name);
        this.password = password;
        this.last_auth = new Date();
        this.enabled = true;
    }
}
