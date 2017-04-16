package com.spx.entity;

import com.google.gson.JsonObject;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by burnaev on 4/16/17.
 */
@Entity
@Table(name = "GAMES")
public class TournamentScheduleEntity implements Parcelable{

        private Long id;
        private TournamentEntity tournament;
        private UserEntity player1;
        private UserEntity player2;
        private Date gameDate;
        private String stage;
        private ArenaEntity arena; // Some doubts here!
        private Long availableTickets;
        private String result;


        @Id
        @Column(name = "ID", nullable = false)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GAMES_SEQ")
        @SequenceGenerator(name = "GAMES_SEQ", sequenceName = "GAMES_SEQ", allocationSize = 1, initialValue = 1)
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }


        @Basic
        @Column(name = "GAME_DATE", nullable = false)
        public Date getGameDate() {
            return gameDate;
        }

        public void setGameDate(Date startDate) {
            this.gameDate = gameDate;
        }


        @Basic
        @Column(name = "STAGE", nullable = true)
        public String getStage() {
            return stage;
        }

        public void setStage(String stage) { this.stage = stage; }


        @Basic
        @Column(name = "RESULT", nullable = true)
        public String getResult() {
        return result;
    }

        public void setResult(String result) { this.result = result; }

        @Basic
        @Column(name = "AVAILABLE_TICKETS", nullable = true)
        public Long getAvailableTickets() {
        return availableTickets;
    }

        public void setAvailableTickets(Long availableTickets) { this.availableTickets = availableTickets; }

        @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        public TournamentEntity getTournament() {
        return tournament;
    }

        public void setTournament(TournamentEntity tournament) {
        this.tournament = tournament;
    }


        @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        public UserEntity getPlayer1() {
        return player1;
    }

        public void setPlayer1(UserEntity player1) {
        this.player1 = player1;
    }

        @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        public UserEntity getPlayer2() {
        return player2;
    }

        public void setPlayer2(UserEntity player2) {
        this.player2 = player2;
    }

        @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        public ArenaEntity getArena() {
        return arena;
    }

        public void setArena(ArenaEntity arena) {
        this.arena = arena;
    }

        @Override
        public JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("id", id);
            json.addProperty("player1", player1.getName());
            json.addProperty("player2", player2.getName());
            json.addProperty("data", gameDate.toString());
            json.addProperty("arena", arena.getName());
            json.addProperty("stage", stage);
            json.addProperty("result", result);
            return json;
        }
}
