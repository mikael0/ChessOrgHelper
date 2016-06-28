/*
package com.spx.dao.mongo;

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

*/
/**
 * Created by timofb on 28-Jun-16.
 *//*

@Repository
public class MongoUserDaoImpl implements UserDao{
    @Autowired
    private MongoOperations mongoOperations;

    private static final String COLLECTION_NAME = "users";

    private static final Logger LOGGER = Logger.getLogger(MongoUserDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<UserEntity> userList() {
        final Query query = new Query();
        return mongoOperations.find(query, UserEntity.class, COLLECTION_NAME);
    }

    @Override
    public UserEntity getUserById(int id) {
        final Query query = new Query(org.springframework.data.mongodb.core.query.Criteria.where("_id").is(id));
        return mongoOperations.findOne(query, UserEntity.class, COLLECTION_NAME);
    }

    @Override
    public List<UserEntity> getUserByLogin(String login) {
        final Query query = new Query(org.springframework.data.mongodb.core.query.Criteria.where("_login").is(login));
        return mongoOperations.find(query, UserEntity.class, COLLECTION_NAME);
    }

    @Override
    public int addUser(final UserEntity userEntity) {
        mongoOperations.insert(userEntity, COLLECTION_NAME);
        return 1;
    }

}
*/
