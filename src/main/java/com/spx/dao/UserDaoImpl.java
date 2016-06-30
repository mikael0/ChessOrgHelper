/*
package com.spx.dao;

import com.spx.entity.UserEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

*/
/**
 * Created by timofb on 21-Jun-16.
 *//*

@Repository
public class UserDaoImpl implements UserDao{

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<UserEntity> userList() {
        final Session session = sessionFactory.openSession();
        try {
            return session
                    .createCriteria(UserEntity.class)
                    .list();
        } finally {
            session.close();
        }

*/
/*        String sql = "SELECT * FROM USERS";
        SQLQuery query = session.createSQLQuery(sql);
        query.addEntity(UserEntity.class);
        return query.list();*//*

    }

    @Override
    public UserEntity getUserById(int id) {
        final Session session = sessionFactory.openSession();
        try {
            return (UserEntity) session.get(UserEntity.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<UserEntity> getUserByLogin(String login) {
        final Session session = sessionFactory.openSession();
        try {
            Criteria crit = session.createCriteria(UserEntity.class);
            crit.add(Restrictions.eq("login",login));
            return crit.list();
        } finally {
            session.close();
        }
    }

    @Override
    public int addUser(final UserEntity userEntity) {
        final Session session = this.sessionFactory.openSession();
        try {
            LOGGER.info("Add user to database in UserDAO:");
            LOGGER.info(userEntity.toString());
            return (Integer) session.save(userEntity);
        } finally {
            session.close();
        }
    }
}
*/
