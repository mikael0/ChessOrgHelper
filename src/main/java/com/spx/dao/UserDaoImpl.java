
package com.spx.dao;

import com.spx.dao.UserDao;
import com.spx.entity.TournamentEntity;
import com.spx.entity.TournamentInterestedUserEntity;
import com.spx.entity.UserEntity;
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.List;



@Repository
@Transactional
public class UserDaoImpl implements UserDao{


    private static final String COLLECTION_NAME = "users";

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    private int userId;
    private String username;
    private String createdBy;
    private Date createdDate;

    public UserDaoImpl() {
    }

    public UserDaoImpl(int userId, String username, String createdBy,
                  Date createdDate) {
        this.userId = userId;
        this.username = username;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
    }



    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
//
    @Override
    public List<UserEntity> userList() {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from users")
                .addEntity(UserEntity.class);
        return q.list();
//        final Query query = new Query();
//        return mongoOperations.find(query, UserEntity.class, COLLECTION_NAME);

    }

    @Override
    public UserEntity getUserById(Long id) {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from users where users.id = :id")
                .addEntity(UserEntity.class)
                .setParameter("id", id);
        return (UserEntity)q.list().get(0);
//        final Query query = new Query(org.springframework.data.mongodb.core.query.Criteria.where("_id").is(id));
//        return mongoOperations.findOne(query, UserEntity.class, COLLECTION_NAME);
    }

    @Override
    public List<UserEntity> getUserByLogin(String login, boolean external) {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from users where users.login = :login")
                      .addEntity(UserEntity.class)
                      .setParameter("login", login);
        return q.list();
    }

    @Override
    public Long addUser(final UserEntity userEntity) {
        sessionFactory.getCurrentSession().save(userEntity);
        return userEntity.getId();

    }

    @Override
    public void updateUser(Long id, UserEntity update) {
        StringBuilder query = new StringBuilder("update users set ");
        if (update.getName() != null)
            query.append("name = :name, ");
        if (update.getPhone() != null)
            query.append("phone = :phone, ");
        if (update.getEmail() != null)
            query.append("email = :email, ");
        if (update.getPassword() != null)
            query.append("password = :password, ");
        query.replace(query.lastIndexOf(","), query.lastIndexOf(",") + 1, "");
        query.append(" where users.id = :id");
        Query q = sessionFactory.getCurrentSession()
                .createSQLQuery(query.toString())
                .addEntity(UserEntity.class);

        q.setParameter("id", id);

        if (update.getName() != null)
            q.setParameter("name", update.getName());
        if (update.getPhone() != null)
            q.setParameter("phone", update.getPhone());
        if (update.getEmail() != null)
            q.setParameter("email", update.getEmail());
        if (update.getPassword() != null)
            q.setParameter("password", update.getPassword());

        q.executeUpdate();

    }

    @Override
    public List<TournamentEntity> getTournamentsForUser(UserEntity user) {
        Query q = sessionFactory.getCurrentSession().createSQLQuery("select * from tournaments where tournaments.id = (select tournament_id from interested where interested.user_id = :id)")
                .addEntity(UserEntity.class)
                .addEntity(TournamentEntity.class)
                .addEntity(TournamentInterestedUserEntity.class)
                .setParameter("id", user.getId());
        return q.list();
    }


}