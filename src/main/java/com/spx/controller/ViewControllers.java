package com.spx.controller;

import com.spx.config.Application;

import com.spx.dao.ParticipationRequestDao;
import com.spx.dao.TournamentDao;
import com.spx.dao.TournamentDaoImpl;
import com.spx.dao.UserDao;
import com.spx.entity.ParticipationRequestEntity;
import com.spx.entity.TournamentEntity;
import com.spx.entity.UserEntity;
import com.spx.service.security.UserDetailsImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;


@SuppressWarnings("HardcodedFileSeparator")
@Controller
@PropertySource(Application.PROPERTIES_PATH)
public class ViewControllers {

    private static final Logger LOGGER = Logger.getLogger(ViewControllers.class);

    @Autowired
    private Environment environment;

    @Autowired
    UserDao userDao;

    @Autowired
    TournamentDao tournamentDao;

    @Autowired
    ParticipationRequestDao requestDao;

    @RequestMapping(value = "/")
    public ModelAndView startPage(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        ModelAndView model = new ModelAndView();
        if (csrfToken != null) {
            model.addObject("_csrf",csrfToken);
        }
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/test")
    public String homePage(/*final ModelMap model*/) {
        return "test";
    }

    @RequestMapping(value = "/table")
    public String parserTestPage(/*final ModelMap model*/) {
        return "tablePage";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutPage() {
        return "index";
    }


    @RequestMapping(value = "/register")
    public String registerPage() {
        return "register";
    }


    @RequestMapping(value = "/tournament_list")
    public String tournamentList(HttpServletRequest request, Principal principal, Model model) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            model.addAttribute("_csrf",csrfToken);
        }
        List<TournamentEntity> tournaments = tournamentDao.getAll();
        if (principal instanceof Authentication) {
            model.addAttribute("userName", ((UserDetails)((Authentication) principal).getPrincipal()).getUsername());
            Object role = ((UserDetails)((Authentication) principal)
                    .getPrincipal()).getAuthorities().toArray()[0];
            model.addAttribute("userRole", role);
            model.addAttribute("tournamentList", tournaments);
            if (role.equals(UserEntity.Roles.ROLE_ORGANIZER.toString())){

            }
            else {

            }
        }
        return "tournament_list";
    }

    @RequestMapping(value = "/create_tournament")
    public String createTournament(Principal principal,
                                   Model model) {
        return "tournament_list";
    }

    @RequestMapping(value = "/profile")
    public String viewProfile(Principal principal,
                              Model model){
        model.addAttribute("user", ((UserDetailsImpl)((Authentication) principal).getPrincipal()).getUser());
        return "profile";
    }

    @RequestMapping(value = "/statistics")
    public String viewStatistics(Principal principal,
                              Model model){
        model.addAttribute("tournaments", tournamentDao.getAll());
        return "statistics";
    }

    @RequestMapping(value = "/housing_settings")
    public String viewHousingSettings(Principal principal,
                                      Model model,
                                      @RequestParam("tournamentId") Long tournamentId){
        TournamentEntity tournament = tournamentDao.getTournamentById(tournamentId);
        model.addAttribute("tournament", tournament);
        return "housing_settings";
    }

    @RequestMapping(value = "/arena_settings")
    public String viewArenaSettings(Principal principal,
                                      Model model,
                                      @RequestParam("tournamentId") Long tournamentId){
        TournamentEntity tournament = tournamentDao.getTournamentById(tournamentId);
        model.addAttribute("tournament", tournament);
        return "arenas_settings";
    }

    @RequestMapping(value = "/schedule")
    public String viewSchedule(Principal principal,
                                    Model model,
                                    @RequestParam("tournamentId") Long tournamentId){
        TournamentEntity tournament = tournamentDao.getTournamentById(tournamentId);
        model.addAttribute("tournament", tournament);
        model.addAttribute("role", ((UserDetailsImpl)((Authentication) principal).getPrincipal()).getUser().getRole());
        return "schedule";
    }

    @RequestMapping(value = "/participants_settings")
    public String viewParticipantsSettings(Principal principal,
                                    Model model,
                                    @RequestParam("tournamentId") Long tournamentId){
        TournamentEntity tournament = tournamentDao.getTournamentById(tournamentId);
        model.addAttribute("tournament", tournament);

        Set<ParticipationRequestEntity> requests = tournament.getParticipationRequests();
        model.addAttribute("requests", requests);

        return "participants_settings";
    }

    @RequestMapping(value = "/test_insert")
    public String testInsert(Principal principal, Model model) {
        TournamentEntity tournament = new TournamentEntity();
        tournament.setName("First World Chess Championship in the name of V.I.Lenin");
        tournament.setCity("Vasiuki");
        tournament.setCountry("USSR");
        tournament.setChiefOrganizer(((UserDetailsImpl)((Authentication) principal).getPrincipal()).getUser());
        tournament.setStartDate(new Date(System.currentTimeMillis()));
        tournament.setEndDate(new Date(System.currentTimeMillis()));
        tournament.setParticipantsNum(666l);
        tournament.setSpectatorsNum(1488l);
        tournament.setMaxParticipantsNum(65536l);
        tournamentDao.addTournament(tournament);
        return "dashboard";
    }

    @RequestMapping("/external")
    public void externalLogin(Principal principal, HttpServletResponse response) {
        try {
            if (userDao.getUserByLogin(principal.getName(), true).size() == 0)  {
                UserEntity externalUser = new UserEntity();
                UserEntity userEntity = new UserEntity();
                userEntity.setLogin(principal.getName());
                userEntity.setExternal(true);
            }
            response.sendRedirect("/dashboard");
        }
        catch (IOException ex) {
            LOGGER.error("Unable to redirect after succesful external authorization");
        }
    }




}