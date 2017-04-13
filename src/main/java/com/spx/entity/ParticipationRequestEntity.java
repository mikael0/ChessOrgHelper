package com.spx.entity;

import com.google.gson.JsonObject;

import javax.persistence.*;

/**
 * Created by mikael0 on 22.11.16.
 */
@Entity
@Table(name = "REQUESTS")
public class ParticipationRequestEntity implements Parcelable {

    private Long id;
    private String name;
    private String surname;
    private Long position;
    private byte[] confirmation;

    private TournamentEntity tournament;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REQUESTS_SEQ")
    @SequenceGenerator(name = "REQUESTS_SEQ", sequenceName = "REQUESTS_SEQ", allocationSize = 1, initialValue = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public TournamentEntity getTournament() {
        return tournament;
    }

    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }


    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("tournamentId", tournament.getId());
        return json;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "SURNAME", nullable = false, length = 50)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "POSITION", nullable = false)
    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    @Basic
    @Column(name = "CONFIRMATION", nullable = false)
    public byte[] getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(byte[] confirmation) {
        this.confirmation = confirmation;
    }
}
