package com.theironyard.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.entities.User;
//import com.theironyard.services.RehearsalSpaceRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.JsonUser;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@RestController
public class RehearsalFinderController {

    @Autowired
    UserRepository users;

//    @Autowired
//    RehearsalSpaceRepository spaces;


    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String home(){
        return "hello";
    }

    @RequestMapping(path = "/sign-up", method = RequestMethod.GET)
    public String loadSignUpPage() {
        return "sign-up";
    }

    @RequestMapping(path = "/add-user", method = RequestMethod.POST)
    public String signUp(@RequestBody String body, HttpServletResponse response, HttpSession session)
            throws PasswordStorage.CannotPerformOperationException, IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(body, User.class);
        users.save(user);
        response.setStatus(201);
        return "New user added to database";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(@RequestBody String body, HttpServletResponse response, HttpSession session)
            throws Exception {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        JsonUser jsonUser = mapper.readValue(body, JsonUser.class);
        String password = jsonUser.getPassword();
        String email = jsonUser.getEmail();
        User user = users.findFirstByEmail(email);
        if (user != null){
            if (!user.verifyPassword(password)) {
                throw new Exception("Wrong credentials!");
            }
        }
        response.setStatus(201);
        return user;
    }

}
