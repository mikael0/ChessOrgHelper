package com.spx.dao;

import com.spx.entity.*;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by mikael0 on 22.11.16.
 */
@Repository
@Transactional
public class TournamentDaoImpl implements TournamentDao {

    @Autowired
    private SessionFactory sessionFactory;

    public TournamentDaoImpl(){

    }

    @Override
    public List<TournamentEntity> getAll() {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from tournaments")
                .addEntity(TournamentEntity.class);
        return q.list();
    }

    @Override
    public TournamentEntity getTournamentById(Long id) {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from tournaments where tournaments.id = :id")
                .addEntity(TournamentEntity.class)
                .setParameter("id", id);
        return (TournamentEntity)q.list().get(0);
    }

    @Override
    public List<TournamentEntity> getTournamentsByOrganizer(Long orgId) {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from tournaments where tournaments.chiefOrganizer.id = :id")
                .addEntity(TournamentEntity.class)
                .setParameter("id", orgId);
        return q.list();
    }


    @Override
    public Long addTournament(TournamentEntity tournament){
        sessionFactory.getCurrentSession().save(tournament);
        return tournament.getId();
    }

    @Override
    public List<UserEntity> getInterestedUsers(TournamentEntity entity) {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from users where users.id = (select user_id from interested where interested.tournament_id = :id)")
                .addEntity(UserEntity.class)
                .addEntity(TournamentEntity.class)
                .addEntity(TournamentInterestedUserEntity.class)
                .setParameter("id", entity.getId());
        return q.list();
    }

    @Override
    public Long addHousing(HousingEntity housing) {
        sessionFactory.getCurrentSession().save(housing);
        for (RoomEntity room : housing.getRooms()) {
            if (room.getBooked() == null)
                room.setBooked(0l);
            sessionFactory.getCurrentSession().save(room);
        }
        return housing.getId();
    }

    @Override
    public Long addArena(ArenaEntity arena) {
        sessionFactory.getCurrentSession().save(arena);
        return arena.getId();
    }

    @Override
    public void updateTournament(TournamentEntity tournament) {
       sessionFactory.getCurrentSession().update(tournament);
    }

}
