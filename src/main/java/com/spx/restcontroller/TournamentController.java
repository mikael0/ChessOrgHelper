package com.spx.restcontroller;

import com.spx.dao.TournamentDao;
import com.spx.dao.UserDao;
import com.spx.email.EmailSender;
import com.spx.entity.TournamentEntity;
import com.spx.entity.UserEntity;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

//    @Autowired
//    EmailSender sender;

//    @Autowired
//    PasswordEncoder encoder;

//    @Autowired
//    private UserDetailsService userDetailsService;


    @ApiOperation(value = "Create Tournament")
    @RequestMapping(value = "/create",  method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<String> createTournament(Principal principal, @RequestBody TournamentEntity tournament) {
//        for (Map.Entry<String, String> entry : tournament.entrySet()) {
//            System.out.println(entry.getKey() + " : " + entry.getValue());
//        }
        System.out.println(tournament.toString());
        System.out.println(tournament.getName());
        System.out.println(tournament.getEndDate());
        System.out.println(tournament.getStartDate());
        if ((tournament == null) || (tournament.getName() == null) || (tournament.getStartDate() == null
        )           || (tournament.getEndDate()) == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        tournament.setChiefOrganizer(((UserDetailsImpl)((Authentication) principal).getPrincipal()).getUser());

        //TODO: check for conflict
//        if () {
//            return new ResponseEntity<String>(HttpStatus.CONFLICT);
//        }

        System.out.println("In tournament create");

        final Long tournamentId = tournamentDao.addTournament(tournament);

        return new ResponseEntity<String>(HttpStatus.OK);
    }






}
