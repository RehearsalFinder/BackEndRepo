package com.theironyard.controllers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.theironyard.entities.RehearsalSpace;
import com.theironyard.entities.User;
import com.theironyard.serializers.RootSerializer;
import com.theironyard.serializers.UserSerializer;
import com.theironyard.services.RehearsalSpaceRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.JsonUser;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return "New user added";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody String body, HttpServletResponse response, HttpSession session)
            throws Exception {
        RootSerializer rootSerializer = new RootSerializer();
        UserSerializer userSerializer = new UserSerializer();
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
        } else throw new Exception("User account does not exist");
        response.setStatus(201);
        return rootSerializer.serializeOne("/login", user, userSerializer);
    }

    // get user from id
    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public Map<String, Object> getUser(@PathVariable("id") String id) throws Exception {
        RootSerializer rootSerializer = new RootSerializer();
        UserSerializer userSerializer = new UserSerializer();
        User user = users.findFirstById(id);
        return rootSerializer.serializeOne("/users/{id}", user, userSerializer);
    }

    // todo add login verification for this route
    @RequestMapping(path = "/add-space", method = RequestMethod.POST)
    public String addSpace(@RequestBody String body, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        RehearsalSpace space = mapper.readValue(body, RehearsalSpace.class);
        spaces.save(space);
        response.setStatus(201);
        return "New rehearsal space added to database";
    }

    // todo add login verification for this route
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

    // todo add login verification for this route
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

//    @RequestMapping(path = "/users", method = RequestMethod.GET)
//    public Map<String, Object> returnUser(@RequestMapping String body, HttpServletResponse response)
//    {
//
//        return
//    }

//    @RequestMapping(path = "/featured-spaces", method = RequestMethod.GET)
//    public ArrayList<RehearsalSpace> home() {
//        RootSerializer rootSerializer = new RootSerializer();
//        UserSerializer userSerializer = new UserSerializer();
//        ArrayList<RehearsalSpace> featuredSpacesList = spaces.findAllByIsFeaturedIsTrue();
//
//        return rootSerializer;
//    }

}
