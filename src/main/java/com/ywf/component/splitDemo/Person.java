package com.ywf.component.splitDemo;

import java.io.Serializable;

public class Person  implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", user=" + user +
                '}';
    }
}