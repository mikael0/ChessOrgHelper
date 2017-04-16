package com.spx.dao;

import com.spx.entity.TournamentEntity;
import com.spx.entity.TournamentInterestedUserEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mikael0 on 15.04.17.
 */
@Repository
@Transactional
public class TournamentInterestedUserDaoImpl implements TournamentInterestedUserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long addInterestedUser(TournamentInterestedUserEntity entity) {
        sessionFactory.getCurrentSession().save(entity);
        return entity.getId();
    }

    @Override
    public TournamentInterestedUserEntity getInterestedById(Long id) {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from interested where interested.id = :id")
                .addEntity(TournamentInterestedUserEntity.class)
                .setParameter("id", id);
        return (TournamentInterestedUserEntity) q.list().get(0);
    }

    @Override
    public void removeInterestedUser(TournamentInterestedUserEntity entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

}
