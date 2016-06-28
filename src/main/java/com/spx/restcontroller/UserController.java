package com.spx.restcontroller;

import com.spx.dao.UserDao;
import com.spx.entity.UserEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * Created by timofb on 28-Jun-16.
 */
@RestController
@RequestMapping("/rest/user")
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder encoder;


    @RequestMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserEntity user) {
        if ((user == null) || (user.getEmail() == null) || (user.getPassword() == null)) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.addUser(user);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
