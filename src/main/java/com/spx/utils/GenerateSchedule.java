package com.spx.utils;

import com.spx.entity.ArenaEntity;
import com.spx.entity.TournamentEntity;
import com.spx.entity.TournamentGameEntity;
import com.spx.entity.TournamentInterestedUserEntity;

import java.lang.Object;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by burnaev on 4/16/17.
 */
public class GenerateSchedule {

    public static List<TournamentGameEntity> generateSchedule(TournamentEntity tournament) {
        TournamentInterestedUserEntity[] interestedUsers = new TournamentInterestedUserEntity[tournament.getInterestedUsers().size()];
        tournament.getInterestedUsers().toArray(interestedUsers);
//
//        if (interestedUsers.length % 16 != 0) {
//            return null;
//        }

        ArrayList<TournamentGameEntity> games = new ArrayList<>();
        for(int i = 0; i < tournament.getMaxParticipantsNum(); i++)
        {
            try {
                games.addAll(generateForOneGroup(i, interestedUsers, tournament));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return games;
    }

    private static List<TournamentGameEntity> generateForOneGroup(int num,TournamentInterestedUserEntity arr[],TournamentEntity tournament){
        ArrayList<TournamentGameEntity> gamesForGroup = new ArrayList<>();
        for( int i = num + 1; i < arr.length; i++){
//            for( int j = 2; j < 4; j++){
//                if( i == j ) j++;
//                if( j < i ) j+=2;
//                if( j >= 4) break;
                TournamentGameEntity game = new TournamentGameEntity();
                game.setPlayer1(arr[num]);
                game.setPlayer2(arr[i]);
                game.setArena(((ArenaEntity)tournament.getArenas().toArray()[new Random().nextInt(tournament.getArenas().size())]));

                Calendar date = Calendar.getInstance();
                Calendar start = Calendar.getInstance();
                start.setTime(tournament.getStartDate());
                Calendar end = Calendar.getInstance();
                end.setTime(tournament.getEndDate());

                date.set(Calendar.HOUR, 10);
                date.set(Calendar.MINUTE, 0);
                date.set(Calendar.SECOND, 0);
                date.set(Calendar.MILLISECOND, 0);

                int year = randBetween(start.get(Calendar.YEAR), end.get(Calendar.YEAR));
                date.set(Calendar.YEAR, year);

                int dayOfYear = randBetween(start.get(Calendar.DAY_OF_YEAR), end.get(Calendar.DAY_OF_YEAR));
                date.set(Calendar.DAY_OF_YEAR, dayOfYear);

                game.setGameDate(date.getTime());

                game.setTournament(tournament);
                game.setAvailableTickets(game.getArena().getMaxSpectators());
                game.setGroup("group");
                game.setStage("Group");

                tournament.getGames().add(game);

                gamesForGroup.add(game);
//            }
        }
        return gamesForGroup;
    }

    private static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

}
