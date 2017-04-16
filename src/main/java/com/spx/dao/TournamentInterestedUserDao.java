package com.spx.dao;

import com.spx.entity.TournamentEntity;
import com.spx.entity.TournamentInterestedUserEntity;
import com.spx.entity.UserEntity;

import java.util.List;

/**
 * Created by mikael0 on 15.04.17.
 */
public interface TournamentInterestedUserDao {
    Long addInterestedUser(TournamentInterestedUserEntity entity);

    TournamentInterestedUserEntity getInterestedById(Long id);
    void removeInterestedUser(TournamentInterestedUserEntity entity);
}
