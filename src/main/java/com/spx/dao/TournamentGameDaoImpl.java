package com.spx.dao;

//import com.spx.entity.TournamentEntity;
import com.spx.entity.TournamentEntity;
import com.spx.entity.TournamentGameEntity;
import com.spx.entity.UserEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by burnaev on 4/16/17.
 */

@Repository
@Transactional
public class TournamentGameDaoImpl implements TournamentGameDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<TournamentGameEntity> getAll()
    {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from games")
                .addEntity(TournamentGameEntity.class);
        return q.list();
    }

    @Override
    public List<TournamentGameEntity> getGamesByPlayer(UserEntity player)
    {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from games where games.player1 = :player1 OR games.player2 = :player1")
                .addEntity(TournamentGameEntity.class)
                .setParameter("player1", player);
        return q.list();
    }

    @Override
    public List<TournamentGameEntity> getGamesByDate(Date date)
    {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from games where games.gameDate = :date")
                .addEntity(TournamentGameEntity.class)
                .setParameter("date", date);
        return q.list();

    }

    @Override
    public TournamentGameEntity getGameById(Long id) {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from games where games.id = :id")
                .addEntity(TournamentGameEntity.class)
                .setParameter("id", id);
        return (TournamentGameEntity)q.list().get(0);
    }

    @Override
    public Long addGame(TournamentGameEntity game)
    {
        sessionFactory.getCurrentSession().save(game);
        return game.getId();
    }

    @Override
    public void updateResult(TournamentGameEntity game)
    {
        sessionFactory.getCurrentSession().update(game);
    }


}
