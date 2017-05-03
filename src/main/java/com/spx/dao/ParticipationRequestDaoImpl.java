package com.spx.dao;

import com.spx.entity.ParticipationRequestEntity;
import com.spx.entity.TournamentEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ParticipationRequestDaoImpl implements ParticipationRequestDao{

    @Autowired
    private SessionFactory sessionFactory;


    public ParticipationRequestDaoImpl(){

    }

//    @Override
//    public  List<ParticipationRequestEntity> listRequestsForTournament(Long tournamentId){
//        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from requests where requests.tournament_id = :id")
//                .addEntity(ParticipationRequestEntity.class)
//                .setParameter("id", tournamentId);
//        return q.list();
//    }

    @Override
    public ParticipationRequestEntity getRequestById(Long id) {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from requests where requests.id = :id")
                .addEntity(ParticipationRequestEntity.class)
                .setParameter("id", id);
        return (ParticipationRequestEntity)q.list().get(0);
    }

    @Override
    public Long addRequest(final ParticipationRequestEntity entity){
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
        return entity.getId();
    }

    @Override
    public void uploadFile(Long id, byte[] data) {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("update requests set confirmation = :data where requests.id = :id")
                .addEntity(ParticipationRequestEntity.class)
                .setParameter("id", id)
                .setParameter("data", data);
        q.executeUpdate();
    }

    @Override
    public void updateRequest(ParticipationRequestEntity entity) {
        sessionFactory.getCurrentSession().update(entity);
    }


    @Override
    public void removeRequest(ParticipationRequestEntity entity) {
        sessionFactory.getCurrentSession().update(entity);
    }
}