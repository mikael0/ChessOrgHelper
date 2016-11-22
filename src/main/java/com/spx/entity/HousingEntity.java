package com.spx.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mikael0 on 22.11.16.
 */
@Entity
@Table(name = "HOUSINGS")
public class HousingEntity {

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
}
