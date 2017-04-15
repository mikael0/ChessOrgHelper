package com.spx.dao;

import com.spx.entity.TournamentEntity;
import com.spx.entity.TournamentInterestedUserEntity;
import com.spx.entity.UserEntity;

import java.util.List;

/**
 * Created by mikael0 on 15.04.17.
 */
public interface TouramentInterestedUserDao {
    Long addInterestedUser(TournamentInterestedUserEntity entity);
}
