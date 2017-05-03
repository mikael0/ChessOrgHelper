package com.spx.restcontroller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spx.dao.*;
import com.spx.entity.*;
import com.spx.service.security.UserDetailsImpl;
import com.spx.utils.GenerateSchedule;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.jets3t.service.model.container.ObjectKeyAndVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.ParseState;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/rest/tournament")
public class TournamentController {

    private static final String emailTemplate = "";

    private static final Logger LOGGER = Logger.getLogger(TournamentController.class);

    @Autowired
    TournamentDao tournamentDao;

    @Autowired
    ParticipationRequestDao requestDao;

    @Autowired
    UserDao userDao;

    @Autowired
    TournamentInterestedUserDao interestedUserDao;

    @Autowired
    TournamentGameDao tournamentGameDao;


    @ApiOperation(value = "Create Tournament")
    @RequestMapping(value = "/create",  method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<String> createTournament(Principal principal, @RequestBody TournamentEntity tournament) {
        if ((tournament == null) || (tournament.getName() == null) || (tournament.getStartDate() == null
        )           || (tournament.getEndDate()) == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        tournament.setChiefOrganizer(((UserDetailsImpl)((Authentication) principal).getPrincipal()).getUser());

        final Long tournamentId = tournamentDao.addTournament(tournament);

        return new ResponseEntity<String>(HttpStatus.OK);
    }



    @ApiOperation(value = "Get Tournament by Id")
    @RequestMapping(value = "/getById",  method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public @ResponseBody String getTournamentById(Principal principal, @RequestBody Long id) {

        TournamentEntity entity = tournamentDao.getTournamentById(id);
        if (entity != null)
            return entity.toJson().toString();
        else
            return null;
    }

    @ApiOperation(value = "Get game by Id")
    @RequestMapping(value = "/game_by_id",  method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public @ResponseBody String getGameById(Principal principal, @RequestBody Long id) {

        TournamentGameEntity entity = tournamentGameDao.getGameById(id);
        if (entity != null)
            return entity.toJson().toString();
        else
            return null;
    }


    @ApiOperation(value = "Add Housing")
    @RequestMapping(value = "/add_housing",  method = RequestMethod.POST)
    @Transactional
    public @ResponseBody ResponseEntity<String> addHousing(Principal principal, @RequestBody HousingEntity housing) {

        if (housing.getTournament() == null)
            housing.setTournament(tournamentDao.getTournamentById(housing.getTournamentId()));
        for (RoomEntity room : housing.getRooms()){
            if (room.getHousing() == null)
                room.setHousing(housing);
        }
        tournamentDao.addHousing(housing);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @ApiOperation(value = "Add Arena")
    @RequestMapping(value = "/add_arena",  method = RequestMethod.POST)
    @Transactional
    public @ResponseBody ResponseEntity<String> addArena(Principal principal, @RequestBody ArenaEntity arena) {

        if (arena.getTournament() == null)
           arena.setTournament(tournamentDao.getTournamentById(arena.getTournamentId()));
        tournamentDao.addArena(arena);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @ApiOperation(value = "Remove arena")
    @RequestMapping(value = "/remove_arena",  method = RequestMethod.POST)
    @Transactional
    public @ResponseBody ResponseEntity<String> removeArena(Principal principal,  @RequestBody  Map<String, String> data) {

        Long arenaId = Long.parseLong(data.get("arenaId"));
        Long tournamentId = Long.parseLong(data.get("tournamentId"));

        TournamentEntity tournament = tournamentDao.getTournamentById(tournamentId);
        tournament.removeArenaById(arenaId);

        tournamentDao.updateTournament(tournament);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @ApiOperation(value = "Approve request")
    @RequestMapping(value = "/approve_request",  method = RequestMethod.POST)
    @Transactional
    public @ResponseBody ResponseEntity<String> approveRequest(Principal principal,
                                                               @RequestBody Map<String, Long> data) {
        Long requestId = data.get("requestId");
        Long tournamentId = data.get("tournamentId");

        TournamentEntity tournament = tournamentDao.getTournamentById(tournamentId);
        ParticipationRequestEntity request = requestDao.getRequestById(requestId);
        UserEntity user = userDao.getUserById(request.getUserId());

        TournamentInterestedUserEntity interestedUser = new TournamentInterestedUserEntity();

        interestedUser.setUser(user);
        interestedUser.setTournament(tournament);
        interestedUser.setRole(TournamentInterestedUserEntity.RolesInTournament.ROLE_PARTICIPANT.toString());
        interestedUser.setRating(0l);
        interestedUser.setWinCount(0l);

        interestedUserDao.addInterestedUser(interestedUser);

        tournament.setParticipantsNum(tournament.getParticipantsNum() + 1);
        tournament.getInterestedUsers().add(interestedUser);
        tournamentDao.updateTournament(tournament);

        request.setViewed(true);

        requestDao.removeRequest(request);
        //TODO: fix

        return new ResponseEntity<String>(HttpStatus.OK);
    }


    @ApiOperation(value = "Remove participant")
    @RequestMapping(value = "/remove_participant",  method = RequestMethod.POST)
    @Transactional
    public @ResponseBody ResponseEntity<String> removeParticipant(Principal principal,
                                                               @RequestBody Map<String, Long> data) {
        Long interestedId = data.get("interestedId");
        Long tournamentId = data.get("tournamentId");

        TournamentEntity tournament = tournamentDao.getTournamentById(tournamentId);
        TournamentInterestedUserEntity interestedUser = interestedUserDao.getInterestedById(interestedId);

        tournament.getInterestedUsers().remove(interestedUser);

        tournamentDao.updateTournament(tournament);

        interestedUserDao.removeInterestedUser(interestedUser);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @ApiOperation(value = "Generate schedule")
    @RequestMapping(value = "/generate_schedule",  method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<String> generateSchedule(Principal principal,
                                   @RequestBody Map<String, Long> data){

        Long tournamentId = data.get("tournamentId");

        TournamentEntity tournament = tournamentDao.getTournamentById(tournamentId);

        List<TournamentGameEntity> games = GenerateSchedule.generateSchedule(tournament);

        tournamentDao.updateTournament(tournament);

        for (TournamentGameEntity game : games) {
            tournamentGameDao.addGame(game);
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @ApiOperation(value = "Buy tickets")
    @RequestMapping(value = "/buy_tickets",  method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<String> buyTickets(Principal principal,
                                                   @RequestBody Map<String, Long> data){

        Long tournamentId = data.get("tournamentId");
        Long gameId = data.get("gameId");
        Long amount = data.get("amount");
        Long roomId = data.get("roomId");

        if (tournamentId != null && gameId != null){
            TournamentEntity tournament = tournamentDao.getTournamentById(tournamentId);
            tournament.setSpectatorsNum(tournament.getSpectatorsNum() + amount);
            tournamentDao.updateTournament(tournament);

            TournamentGameEntity game = tournamentGameDao.getGameById(gameId);
            game.setAvailableTickets(game.getAvailableTickets() - amount);
            tournamentGameDao.updateResult(game);
        }

        if (roomId != null) {
            RoomEntity room = tournamentDao.getRoomById(roomId);
            room.setAmount(room.getAmount() - amount);
            room.setBooked(room.getBooked() + amount);
            tournamentDao.updateRoom(room);
        }


        return new ResponseEntity<String>(HttpStatus.OK);
    }



}
