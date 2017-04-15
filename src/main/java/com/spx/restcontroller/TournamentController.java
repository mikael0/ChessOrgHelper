package com.spx.restcontroller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.spx.dao.TournamentDao;
import com.spx.dao.UserDao;
import com.spx.email.EmailSender;
import com.spx.entity.*;
import com.spx.service.security.UserDetailsImpl;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
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
import java.util.Map;


@RestController
@RequestMapping("/rest/tournament")
public class TournamentController {

    private static final String emailTemplate = "";

    private static final Logger LOGGER = Logger.getLogger(TournamentController.class);

    @Autowired
    TournamentDao tournamentDao;



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


}
