package com.spx.entity;

import com.google.gson.JsonObject;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "INTERESTED")
public class TournamentInterestedUserEntity implements Parcelable {

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        return json;
    }

    public enum RolesInTournament{
        ROLE_PARTICIPANT,
        ROLE_SPECTATOR,
    }

    private Long id;
    private String role;

    private UserEntity user;

    private TournamentEntity tournament;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INTERESTED_SEQ")
    @SequenceGenerator(name = "INTERESTED_SEQ", sequenceName = "INTERESTED_SEQ", allocationSize = 1, initialValue = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ROLE", nullable = false)
    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public TournamentEntity getTournament() {
        return tournament;
    }

    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TournamentInterestedUserEntity that = (TournamentInterestedUserEntity) o;

        if (id != that.id) return false;
        
        return true;
    }

}
