package com.spx.dao;

import com.spx.entity.ArenaEntity;
import com.spx.entity.HousingEntity;
import com.spx.entity.TournamentEntity;
import com.spx.entity.UserEntity;
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

//    private Long id;
//    private String name;
//    private Date startDate;
//    private Date endDate;

    public TournamentDaoImpl(){

    }

//    public TournamentDaoImpl(Long id, String name, Date start, Date end){
//        this.id = id;
//        this.name = name;
//        this.startDate = start;
//        this.endDate = end;
//    }


    @Override
    public List<TournamentEntity> getAll() {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from tournaments")
                .addEntity(TournamentEntity.class);
        return q.list();
    }

    @Override
    public TournamentEntity getTournamentById(Long id) {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from tournaments where tournaments.id = :id")
                .addEntity(UserEntity.class)
                .setParameter("id", id);
        return (TournamentEntity)q.list().get(0);
    }

    @Override
    public List<TournamentEntity> getTournamentsByOrganizer(Long orgId) {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from tournaments where tournaments.chiefOrganizer.id = :id")
                .addEntity(UserEntity.class)
                .setParameter("id", orgId);
        return q.list();
    }

//    @Override
//    public Long addArena(ArenaEntity arena) {
//
//    }
//
//    @Override
//    public Long addHousing(HousingEntity housing) {
//
//    }

    @Override
    public Long addTournament(TournamentEntity tournament){
        sessionFactory.getCurrentSession().save(tournament);
        return tournament.getId();
    }

//    @Override
//    public void setParticipantsNum(Long num, Long tournamentId) {
//        sessionFactory.getCurrentSession()
//                .createSQLQuery("update tournaments t set t.participants_num = :num where t.id = :id")
//                .setParameter("id", tournamentId)
//                .setParameter("num", num)
//                .executeUpdate();
//    }
//
//    @Override
//    public void setSpectatorsNum(Long num, Long tournamentId) {
//        sessionFactory.getCurrentSession()
//                .createSQLQuery("update tournaments t set t.spectators_num = :num where t.id = :id")
//                .setParameter("id", tournamentId)
//                .setParameter("num", num)
//                .executeUpdate();
//    }
//
//    @Override
//    public void setMaxParticipantsNum(Long num, Long tournamentId) {
//        sessionFactory.getCurrentSession()
//                .createSQLQuery("update tournaments t set t.max_participants_num = :num where t.id = :id")
//                .setParameter("id", tournamentId)
//                .setParameter("num", num)
//                .executeUpdate();
//    }
}
