package com.utp.projekt.Entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Marcin on 24.11.2016.
 */

public class Login {

    private Long id;
    private String login;
    private String passwd;
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Login(Long id, String login, String passwd, String type) {
        this.id = id;
        this.login = login;
        this.passwd = passwd;
        this.type = type;
    }

    public Login() {
    }
}
