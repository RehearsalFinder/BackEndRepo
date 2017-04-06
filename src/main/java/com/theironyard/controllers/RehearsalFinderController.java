package com.theironyard.controllers;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.entities.User;
//import com.theironyard.services.RehearsalSpaceRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String signUp(@RequestBody String body, HttpServletResponse response)
            throws PasswordStorage.CannotPerformOperationException, IOException {
        response.setContentType("application/json");
        response.setStatus(211);
        // parse through new user data and add to users table in db

        System.out.println(body);

        return "{\"this\":\"should be json\"}";
    }


}
