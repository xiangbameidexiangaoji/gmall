package com.sxt.rabbitmq.bean;

import java.io.Serializable;

/**
 * @author 李涵林
 * @data 2020/7/14 15:46
 */
public class User implements Serializable {
    private String userName;
    private String email;

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
    public User(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

