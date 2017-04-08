package com.theironyard.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.entities.RehearsalSpace;
import com.theironyard.entities.User;
import com.theironyard.services.RehearsalSpaceRepository;
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

    @Autowired
    RehearsalSpaceRepository spaces;


    @RequestMapping(path = "/add-user", method = RequestMethod.POST)
    public String signUp(@RequestBody String body, HttpServletResponse response, HttpSession session)
            throws Exception {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(body, User.class);
        String userEmail = user.getEmail();
        User user1 = users.findFirstByEmail(userEmail);
        if (user1 == null) {
            users.save(user);
            response.setStatus(201);
        } else throw new Exception("Email address is associated with an existing account!");
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
        if (user != null) {
            if (!user.verifyPassword(password)) {
                throw new Exception("Wrong credentials!");
            }
        }
        response.setStatus(201);
        return user;
    }

    @RequestMapping(path = "/add-space", method = RequestMethod.POST)
    public String addSpace(@RequestBody String body, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        RehearsalSpace space = mapper.readValue(body, RehearsalSpace.class);
        spaces.save(space);
        response.setStatus(201);
        return "New rehearsal space added to database";
    }

    @RequestMapping(path = "/delete-user", method = RequestMethod.DELETE)
    public String deleteUser(@RequestBody String body, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        User selectedUser = mapper.readValue(body, User.class);
        String email = selectedUser.getEmail();
        User deleteUser = users.findFirstByEmail(email);
        users.delete(deleteUser);
        response.setStatus(201);
        return "User removed";
    }

    @RequestMapping(path = "/delete-space", method = RequestMethod.DELETE)
    public String deleteSpace(@RequestBody String body, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        RehearsalSpace selectedSpace = mapper.readValue(body, RehearsalSpace.class);
        String email = selectedSpace.getHostEmail();
        RehearsalSpace deleteSpace = spaces.findFirstByHostEmail(email);
        spaces.delete(deleteSpace);
        response.setStatus(201);
        return "Space removed";
    }

    @RequestMapping(path = "/browse-all", method = RequestMethod.GET)
    public ArrayList<RehearsalSpace> browseAll() {
        ArrayList<RehearsalSpace> spacesList = (ArrayList<RehearsalSpace>) spaces.findAll();
        return spacesList;
    }

    // todo return a list of featured rehearsal spaces (5?) on the home page

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ArrayList<RehearsalSpace> home() {


        return featuredSpacesList;
    }



}
