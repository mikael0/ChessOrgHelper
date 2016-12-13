package com.spx.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mikael0 on 22.11.16.
 */
@Entity
@Table(name = "TOURNAMENTS")
public class TournamentEntity {

    private Long id;
    private String name;
    private Long maxParticipantsNum;
    private Date startDate;
    private Date endDate;
    private String country;
    private String city;
    private Long participantsNum = 0l;
    private Long spectatorsNum = 0l;

    private UserEntity chiefOrganizer;

    private Set<ArenaEntity> arenas = new HashSet<ArenaEntity>();
    private Set<HousingEntity> housings = new HashSet<HousingEntity>();

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOURNAMENTS_SEQ")
    @SequenceGenerator(name = "TOURNAMENTS_SEQ", sequenceName = "TOURNAMENTS_SEQ", allocationSize = 1, initialValue = 1)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 100)
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "CITY", nullable = false, length = 20)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "COUNTRY", nullable = false, length = 50)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "START_DATE", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "END_DATE", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "PARTICIPANTS_NUM", nullable = true)
    public Long getParticipantsNum() {
        return participantsNum;
    }

    public void setParticipantsNum(Long participantsNum) {
        this.participantsNum = participantsNum;
    }

    @Basic
    @Column(name = "MAX_PARTICIPANTS_NUM", nullable = false)
    public Long getMaxParticipantsNum() {
        return maxParticipantsNum;
    }

    public void setMaxParticipantsNum(Long participantsNum) {
        this.maxParticipantsNum = participantsNum;
    }

    @Basic
    @Column(name = "SPECTATORS_NUM", nullable = true)
    public Long getSpectatorsNum() {
        return spectatorsNum;
    }

    public void setSpectatorsNum(Long spectatorsNum) {
        this.spectatorsNum = spectatorsNum;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public UserEntity getChiefOrganizer() {
        return chiefOrganizer;
    }

    public void setChiefOrganizer(UserEntity chiefOrganizer) {
        this.chiefOrganizer = chiefOrganizer;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tournament")
    public Set<ArenaEntity> getArenas() {
        return arenas;
    }

    public void setArenas(Set<ArenaEntity> arenas) {
        this.arenas = arenas;
    }

    public void addArena(ArenaEntity arena){
        arenas.add(arena);
    }

    public void removeArena(ArenaEntity arena){
        arenas.remove(arena);
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tournament")
    public Set<HousingEntity> getHousings() {
        return housings;
    }

    public void setHousings(Set<HousingEntity> housings) {
        this.housings = housings;
    }

    public void addHousing(HousingEntity housing){
        housings.add(housing);
    }

    public void removeHousing(HousingEntity housing){
        housings.remove(housing);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        TournamentEntity that = (TournamentEntity) o;
//
//        if (id != that.id) return false;
//
//        return true;
//    }
//
//
//    @Override
//    public int hashCode() {
//        int result = id.hashCode();
//
//        return result;
//    }


}
