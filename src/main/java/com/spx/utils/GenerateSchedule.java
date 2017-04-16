package com.spx.utils;

import com.spx.entity.TournamentEntity;
import com.spx.entity.TournamentGameEntity;
import com.spx.entity.TournamentInterestedUserEntity;

import java.lang.Object;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by burnaev on 4/16/17.
 */
public class GenerateSchedule {

    public static void generateSchedule(TournamentEntity tournament) {
        TournamentInterestedUserEntity[] interestedUsers = (TournamentInterestedUserEntity[])tournament.getInterestedUsers().toArray();

    for(int i = 0; i < tournament.getMaxParticipantsNum()/4; i++)
    {
        generateForOneGroup(i, Arrays.copyOfRange(interestedUsers,i*4,i*4+3),tournament);
    }

    }
    private static void generateForOneGroup(int group,TournamentInterestedUserEntity arr[],TournamentEntity tournament){
        for( int i = 1; i < 4; i++){
            for( int j = 2; j < 5; j++){
                if( i == j ) j++;
                if( j < i ) j+=2;
                TournamentGameEntity game = new TournamentGameEntity();
                game.setPlayer1(arr[i].getUser());
                game.setPlayer2(arr[j].getUser());
                //game.setArena(tournament.getArenas().toArray());
                //long start = tournament.getStartDate().toEpochDay();
                //Date randomDate = new Date(ThreadLocalRandom.current().nextLong(tournament.getStartDate(), tournament.getEndDate()));
                //game.setGameDate();
            }
        }
    }



}
