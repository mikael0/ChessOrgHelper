package com.spx.dao;

import com.spx.entity.UserEntity;

import java.util.List;

/**
 * Created by timofb on 21-Jun-16.
 */
public interface UserDao {
    List<UserEntity> userList();
    UserEntity getUserById(int id);
    List<UserEntity> getUserByLogin(String login);
    int addUser(final UserEntity userEntity);

}