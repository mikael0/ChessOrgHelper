package com.spx.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mikael0 on 22.11.16.
 */
@Entity
@Table(name = "ARENAS")
public class ArenaEntity {

    private Long id;
    private String address;
    private Long maxSpectators;

    private TournamentEntity tournament;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARENAS_SEQ")
    @SequenceGenerator(name = "ARENAS_SEQ", sequenceName = "ARENAS_SEQ", allocationSize = 1, initialValue = 1)
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

    @Basic
    @Column(name = "MAX_SPECTATORS", nullable = false)
    public Long getMaxSpectators() {
        return maxSpectators;
    }

    public void setMaxSpectators(Long maxSpectators) {
        this.maxSpectators = maxSpectators;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public TournamentEntity getTournament() {
        return tournament;
    }

    public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }


}
