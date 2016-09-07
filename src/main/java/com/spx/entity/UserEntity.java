package com.spx.entity;

import javax.persistence.*;

/**
 * Created by timofb on 21-Jun-16.
 */
@Entity
@Table(name = "USERS")
public class UserEntity {
    private String id;
    private String login;
    private String password;
    private String email;
    private boolean activated;
    private boolean external;

    @Id
    @Column(name = "_ID", nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "LOGIN", nullable = false, length = 20)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "PASSWORD", nullable = false, length = 300)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "EMAIL", nullable = false, length = 30)
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "EXTERNAL")
    public boolean isExternal() {
        return external;
    }

    public void setExternal(boolean isExternal) {
        this.external = isExternal;
    }

    @Basic
    @Column(name = "ACTIVATED", nullable = false)
    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (activated != that.activated) return false;
        if (external != that.external) return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (activated != true ? 1 : 0);
        return result;
    }
}
