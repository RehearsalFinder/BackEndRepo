package com.theironyard.controllers;

import com.theironyard.entities.User;
//import com.theironyard.services.RehearsalSpaceRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

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
            throws PasswordStorage.CannotPerformOperationException {
//        User user = new User(firstName, lastName, password, email, birthday);
//        users.save(user);
        response.setContentType("application/json");
        response.setStatus(211);
        System.out.println(body);
        JsonParser p;

        return "{\"this\":\"should be json\"}";
    }


}
