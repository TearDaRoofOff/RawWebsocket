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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (!getPassword().equals(user.getPassword())) return false;
        return getName().equals(user.getName());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }
}
