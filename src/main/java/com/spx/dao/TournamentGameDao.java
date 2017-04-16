package com.spx.dao;

import com.spx.entity.*;

import java.util.Date;
import java.util.List;

/**
 * Created by burnaev on 4/16/17.
 */
public interface TournamentScheduleDao {

    List<TournamentScheduleEntity> getAll();
    List<TournamentScheduleEntity> getGamesByPlayer(UserEntity player);
    List<TournamentScheduleEntity> getGamesByDate(Date date);

    Long addGame();
    Long updateResult(Long gameId, String result);
}

