package com.spx.dao;

import com.spx.entity.*;
import org.jets3t.service.utils.gatekeeper.GatekeeperMessage;

import java.util.List;

/**
 * Created by mikael0 on 22.11.16.
 */
public interface TournamentDao {

    List<TournamentEntity> getAll();
    List<TournamentEntity> getTournamentsByOrganizer(Long orgId);
    TournamentEntity getTournamentById(Long id);

    RoomEntity getRoomById(Long id);

    Long addTournament(TournamentEntity tournament);

    List<UserEntity> getInterestedUsers(TournamentEntity entity);

    Long addHousing(HousingEntity housing);
    Long addArena(ArenaEntity arena);

    void updateTournament(TournamentEntity tournament);
    void updateRoom(RoomEntity room);
}

