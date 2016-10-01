package com.demsmobile.vanpedia.model;

/**
 * Created by Diego on 2/4/2016.
 */
public class User {
    private long id;
    private String email;

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    private String username;

    public long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

//keytool -exportcert -list -v -alias vanpedia -keystore ~/.android/debug.keystore