package com.spx.dao;

import com.spx.entity.TournamentEntity;
import com.spx.entity.TournamentScheduleEntity;
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
public class TournamentScheduleDaoImpl {

    @Autowired
    private SessionFactory sessionFactory;

    List<TournamentScheduleEntity> getAll()
    {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from games")
                .addEntity(TournamentScheduleEntity.class);
        return q.list();
    }
    List<TournamentScheduleEntity> getGamesByPlayer(UserEntity player)
    {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from games where games.player1 = :player1 OR games.player2 = :player1")
                .addEntity(TournamentScheduleEntity.class)
                .setParameter("player1", player);
        return q.list();
    }
    List<TournamentScheduleEntity> getGamesByDate(Date date)
    {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from games where games.gameDate = :date")
                .addEntity(TournamentScheduleEntity.class)
                .setParameter("date", date);
        return q.list();

    }

    Long addGame(TournamentScheduleEntity game)
    {
        sessionFactory.getCurrentSession().save(game);
        return game.getId();
    }
    Long updateResult(Long gameId, String result)
    {}
}
