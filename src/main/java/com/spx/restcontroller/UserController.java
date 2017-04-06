package com.spx.restcontroller;

import com.spx.dao.UserDao;
import com.spx.email.EmailEntity;
import com.spx.email.EmailSender;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;


@RestController
@RequestMapping("/rest/user")
public class UserController {

    private static final String emailTemplate = "";

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    @Autowired
    UserDao userDao;

    @Autowired
    EmailSender sender;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @SuppressWarnings("Hardcoded email template")
    @ApiOperation(value = "Register new user")
    @RequestMapping(value = "/register",  method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<String> registerUser(@RequestBody UserEntity user) {
        if ((user == null) || (user.getEmail() == null) || (user.getPassword() == null)) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        user.setExternal(false);
        if (userDao.getUserByLogin(user.getLogin(), false).size() > 0) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActivated(true);
        user.setRole(UserEntity.Roles.ROLE_SPECTATOR.toString());

        final Long user_id = userDao.addUser(user);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @ApiOperation(value = "Update user data")
    @RequestMapping(value = "/update",  method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<String> updateUser(Principal principal,
                                             @RequestBody UserEntity user) {

        if (user == null)
            user = new UserEntity();

       LOGGER.debug("user: "  + user);
       if (user.getPassword() != null)
          user.setPassword(encoder.encode(user.getPassword()));

        userDao.updateUser(((UserDetailsImpl)((Authentication) principal).getPrincipal()).getUser().getId(), user);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping("/details")
    public Principal user(Principal principal) {
        return principal;
    }

    @ApiOperation(value = "Form login")
    @Transactional
    @RequestMapping(value = "/formlogin",  method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> formLogin(HttpServletRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String user = parameterMap.get("username")[0];
        String password = parameterMap.get("password")[0];
        if ((user != null) && (password != null)) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user);

                if (userDetails.isEnabled() && encoder.matches(password, userDetails.getPassword())) {

                    doAutoLogin(userDetails, password, request);

                    return new ResponseEntity<String>(HttpStatus.OK);
                }
            }
            catch (UsernameNotFoundException ex) {
                return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
            }

        }
        return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

    }

    private void doAutoLogin(UserDetails userDetails, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authRequest.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authRequest);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }



}
