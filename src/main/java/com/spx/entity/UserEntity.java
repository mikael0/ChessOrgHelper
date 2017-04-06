package com.spx.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "USERS")
public class UserEntity {

    public enum Roles{
        ROLE_PARTICIPANT,
        ROLE_SPECTATOR,
        ROLE_ORGANIZER
    }

    private Long id;
    private String login;
    private String password;
    private String email;
    private String name;
    private boolean activated;
    private boolean external;
    private String role;
    private String phone;

    private Set<TournamentEntity> tournaments;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1, initialValue = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    @Column(name = "PHONE", nullable = false, length = 30)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Basic
    @Column(name = "ROLE", nullable = false)
    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chiefOrganizer")
    public Set<TournamentEntity> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Set<TournamentEntity> tournaments) {
        this.tournaments = tournaments;
    }

    @Basic
    @Column(name = "NAME", length=25)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("name=").append(name)
          .append(" password=").append(password)
          .append(" phone=").append(phone)
          .append(" email=").append(email);

        return sb.toString();
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
