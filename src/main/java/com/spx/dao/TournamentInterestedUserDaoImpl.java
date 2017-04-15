package com.spx.dao;

import com.spx.entity.TournamentInterestedUserEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mikael0 on 15.04.17.
 */
@Repository
@Transactional
public class TournamentInterestedUserDaoImpl implements TouramentInterestedUserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long addInterestedUser(TournamentInterestedUserEntity entity) {
        sessionFactory.getCurrentSession().save(entity);
        return entity.getId();
    }

}
