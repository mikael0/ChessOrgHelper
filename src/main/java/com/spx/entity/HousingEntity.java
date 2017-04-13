package com.spx.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jdk.nashorn.api.scripting.JSObject;
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;
import org.codehaus.jettison.json.JSONObject;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mikael0 on 22.11.16.
 */
@Entity
@Table(name = "HOUSINGS")
public class HousingEntity implements Parcelable{

    private Long id;
    private String address;

    private Set<RoomEntity> rooms = new HashSet<RoomEntity>();

    private TournamentEntity tournament;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HOUSINGS_SEQ")
    @SequenceGenerator(name = "HOUSINGS_SEQ", sequenceName = "HOUSINGS_SEQ", allocationSize = 1, initialValue = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ADDRESS", nullable = false, length = 50)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public TournamentEntity getTournament() {
        return tournament;
    }

    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "housing")
    public Set<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(Set<RoomEntity> rooms) {
        this.rooms = rooms;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("address", address);
        JsonArray rooms = new JsonArray();
        for (RoomEntity room : this.rooms) {
            rooms.add(room.toJson());
        }
        json.add("rooms", rooms);
        json.addProperty("tournamentId", tournament.getId());
        return json;
    }
}
