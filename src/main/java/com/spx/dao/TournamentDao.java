package com.spx.dao;

import com.spx.entity.ArenaEntity;
import com.spx.entity.HousingEntity;
import com.spx.entity.TournamentEntity;

import java.util.List;

/**
 * Created by mikael0 on 22.11.16.
 */
public interface TournamentDao {

    List<TournamentEntity> getAll();
    List<TournamentEntity> getTournamentsByOrganizer(Long orgId);
    TournamentEntity getTournamentById(Long id);

//    Long addArena(ArenaEntity arena);
//    Long addHousing(HousingEntity housing);
    Long addTournament(TournamentEntity tournament);

//    void setParticipantsNum(Long num, Long tournamentId);
//    void setSpectatorsNum(Long num, Long tournamentId);
//    void setMaxParticipantsNum(Long num, Long tournamentId);

}
