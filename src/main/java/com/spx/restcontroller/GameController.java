package com.spx.restcontroller;

import com.spx.dao.TournamentGameDao;
import com.spx.dao.TournamentInterestedUserDao;
import com.spx.dao.UserDao;
import com.spx.entity.ArenaEntity;
import com.spx.entity.TournamentGameEntity;
import com.spx.entity.TournamentInterestedUserEntity;
import com.spx.entity.UserEntity;
import com.spx.service.security.UserDetailsImpl;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;


@RestController
@RequestMapping("/rest/game")
public class GameController {

    private static final String emailTemplate = "";

    private static final Logger LOGGER = Logger.getLogger(GameController.class);

    @Autowired
    UserDao userDao;

    @Autowired
    TournamentGameDao gameDao;

    @Autowired
    TournamentInterestedUserDao interestedDao;

    @Autowired
    private UserDetailsService userDetailsService;

    @ApiOperation(value = "Enter results")
    @RequestMapping(value = "/enter_results",  method = RequestMethod.POST)
    @Transactional
    public @ResponseBody
    ResponseEntity<String> addArena(Principal principal, @RequestBody Map<String, Long> data) {

        Long gameId = data.get("gameId");
        Long winner1Id = data.get("winner1");
        Long winner2Id = data.get("winner2");

        TournamentGameEntity game = gameDao.getGameById(gameId);

        TournamentInterestedUserEntity player1 = interestedDao.getInterestedById(winner1Id);
        player1.setWinCount(player1.getWinCount() + 1);
        TournamentInterestedUserEntity player2 = null;
        if (winner2Id != null) {
            player2 = interestedDao.getInterestedById(winner2Id);
            player2.setWinCount(player2.getWinCount() + 1);
            player2.setRating(player2.getRating() + 1);
            player1.setRating(player1.getRating() + 1);
            game.setResult("Draw");
        } else {
            player1.setRating(player1.getRating() + 2);
            game.setResult(player1.getUser().getName());
        }

        gameDao.updateResult(game);


        return new ResponseEntity<String>(HttpStatus.OK);
    }


}
