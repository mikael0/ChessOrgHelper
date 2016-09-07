
package com.spx.dao;

import com.spx.dao.UserDao;
import com.spx.entity.UserEntity;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by timofb on 28-Jun-16.
 */

@Repository
public class UserDaoImpl implements UserDao{
    @Autowired
    private MongoOperations mongoOperations;

    private static final String COLLECTION_NAME = "users";

    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<UserEntity> userList() {
        final Query query = new Query();
        return mongoOperations.find(query, UserEntity.class, COLLECTION_NAME);
    }

    @Override
    public UserEntity getUserById(String id) {
        final Query query = new Query(org.springframework.data.mongodb.core.query.Criteria.where("_id").is(id));
        return mongoOperations.findOne(query, UserEntity.class, COLLECTION_NAME);
    }

    @Override
    public List<UserEntity> getUserByLogin(String login, boolean external) {
        final Query query = new Query(org.springframework.data.mongodb.core.query.Criteria.where("login").is(login).where("external").is(external));
        return mongoOperations.find(query, UserEntity.class, COLLECTION_NAME);
    }

    @Override
    public String addUser(final UserEntity userEntity) {
        mongoOperations.insert(userEntity, COLLECTION_NAME);
        return userEntity.getId();
    }

    @Override
    public void activate(String id) {
        UserEntity user = getUserById(id);
        user.setActivated(true);
        mongoOperations.save(user, COLLECTION_NAME);
    }
}