package com.spx.dao;

import com.spx.entity.*;

import java.util.Date;
import java.util.List;

/**
 * Created by burnaev on 4/16/17.
 */
public interface TournamentGameDao {

    List<TournamentGameEntity> getAll();
    List<TournamentGameEntity> getGamesByPlayer(UserEntity player);
    List<TournamentGameEntity> getGamesByDate(Date date);

    Long addGame(TournamentGameEntity game);
    void updateResult(TournamentGameEntity game);
}

