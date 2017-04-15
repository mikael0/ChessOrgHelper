package com.spx.dao;

import com.spx.entity.ArenaEntity;
import com.spx.entity.HousingEntity;
import com.spx.entity.TournamentEntity;
import com.spx.entity.UserEntity;

import java.util.List;

/**
 * Created by mikael0 on 22.11.16.
 */
public interface TournamentDao {

    List<TournamentEntity> getAll();
    List<TournamentEntity> getTournamentsByOrganizer(Long orgId);
    TournamentEntity getTournamentById(Long id);

    Long addTournament(TournamentEntity tournament);

    List<UserEntity> getInterestedUsers(TournamentEntity entity);

    Long addHousing(HousingEntity housing);
    Long addArena(ArenaEntity arena);

    void updateTournament(TournamentEntity tournament);
}

