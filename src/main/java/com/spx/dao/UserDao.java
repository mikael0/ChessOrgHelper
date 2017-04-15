package com.spx.dao;

import com.spx.entity.TournamentEntity;
import com.spx.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserDao {
    List<UserEntity> userList();
    UserEntity getUserById(Long id);
    List<UserEntity> getUserByLogin(String login, boolean external);
//    void activate(String id);
    Long addUser(final UserEntity userEntity);

    void updateUser(Long id, UserEntity update);

    List<TournamentEntity> getTournamentsForUser(UserEntity user);
}