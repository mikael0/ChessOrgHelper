package com.spx.dao;

import com.spx.entity.ParticipationRequestEntity;
import com.spx.entity.UserEntity;

import java.util.List;


public interface ParticipationRequestDao {
//    List<ParticipationRequestEntity> listRequestsForTournament(Long tournamentId);
    ParticipationRequestEntity getRequestById(Long id);

    Long addRequest(final ParticipationRequestEntity entity);
    void uploadFile(Long id, byte[] data);

    void removeRequest(final ParticipationRequestEntity entity);
}