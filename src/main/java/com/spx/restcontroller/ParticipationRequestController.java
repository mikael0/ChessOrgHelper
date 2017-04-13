package com.spx.restcontroller;

import com.spx.dao.ParticipationRequestDao;
import com.spx.dao.TournamentDao;
import com.spx.entity.ParticipationRequestEntity;
import com.spx.entity.TournamentEntity;
import com.spx.service.security.UserDetailsImpl;
import com.wordnik.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.security.Principal;


@RestController
@RequestMapping("/rest/apply")
public class ParticipationRequestController {

    private static final String emailTemplate = "";

    private static final Logger LOGGER = Logger.getLogger(ParticipationRequestController.class);

    @Autowired
    ParticipationRequestDao participationRequestDao;

//    @Autowired
//    EmailSender sender;

//    @Autowired
//    PasswordEncoder encoder;

//    @Autowired
//    private UserDetailsService userDetailsService;


    @ApiOperation(value = "Apply")
    @RequestMapping(value = "/request",  method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Long> createTournament(Principal principal,
                                                   @RequestBody ParticipationRequestEntity request) {



        Long id = participationRequestDao.addRequest(request);

        return new ResponseEntity<Long>(id, HttpStatus.OK);
    }


    @ApiOperation(value = "Upload file")
    @RequestMapping(value = "/confiramtion",  method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional
    public ResponseEntity<String> createTournament(Principal principal,
                                                   @RequestParam Long id,
                                                   @RequestParam CommonsMultipartFile[] fileUpload) {

        ParticipationRequestEntity request = participationRequestDao.getRequestById(id);

        if (fileUpload != null && fileUpload.length > 0) {
            for (CommonsMultipartFile aFile : fileUpload){

                System.out.println("Saving file: " + aFile.getOriginalFilename());

                request.setConfirmation(aFile.getBytes());

                participationRequestDao.addRequest(request);
            }
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }




}
