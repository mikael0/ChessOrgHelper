package com.spx.restcontroller;

import com.spx.dao.UserDao;
import com.spx.email.EmailEntity;
import com.spx.email.EmailSender;
import com.spx.entity.UserEntity;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

/**
 * Created by timofb on 28-Jun-16.
 */
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
    public ResponseEntity<String> registerUser(@RequestBody UserEntity user) {
        if ((user == null) || (user.getEmail() == null) || (user.getPassword() == null)) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        if (userDao.getUserByLogin(user.getLogin()).size() > 0) {
            return new ResponseEntity<String>(HttpStatus.CONFLICT);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActivated(false);

        final String user_id = userDao.addUser(user);

        final String message = "<html><h1>Activate your spx account</h1><a href=\"http://localhost:8080/rest/user/activate/" + user_id + "\"> here</a></html>";
        final EmailEntity email = new EmailEntity(user.getEmail(), message, "Stroy Project X account activation");

        sender.sendEmail(email);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping("/activate/{id}")
    public ResponseEntity<String> activateUser(@PathVariable("id") String user_id, HttpServletResponse response) {
        userDao.activate(user_id);
        try {
            response.sendRedirect("/");
        }
        catch (IOException ex) {
            LOGGER.error("Unable to redirect after succesful account activation.");
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping("/details")
    public Principal user(Principal principal) {
        return principal;
    }

    @ApiOperation(value = "Form login")
    @RequestMapping(value = "/formlogin",  method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> formLogin(HttpServletRequest request, ModelMap model) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String user = parameterMap.get("username")[0];
        String password = parameterMap.get("password")[0];
        if ((user != null) && (password != null)) {
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user);
                if (userDetails.isEnabled() && encoder.matches(password, userDetails.getPassword())) {
                    doAutoLogin(user, password, request);
                    return new ResponseEntity<String>(HttpStatus.OK);
                }
            }
            catch (UsernameNotFoundException ex) {
                return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
            }

        }
        return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

    }

    private void doAutoLogin(String username, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(authRequest);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
    }


}
