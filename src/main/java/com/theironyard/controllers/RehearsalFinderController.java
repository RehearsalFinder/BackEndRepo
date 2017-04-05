package com.theironyard.controllers;

import com.theironyard.entities.User;
//import com.theironyard.services.RehearsalSpaceRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Scanner;

@Controller
public class RehearsalFinderController {

    @Autowired
    UserRepository users;

//    @Autowired
//    RehearsalSpaceRepository spaces;


    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(HttpSession session, Model model){
        String userName = (String) session.getAttribute("userName");
        User user = users.findFirstByFirstName(userName);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "home";
    }

    @RequestMapping(path = "/sign-up", method = RequestMethod.GET)
    public String loadSignUpPage(Model model) {
        return "sign-up";
    }

    @RequestMapping(path = "/add-user", method = RequestMethod.POST)
    public String signUp(HttpSession session, String firstName, String lastName,String password,
                         String email, Date birthday) throws PasswordStorage.CannotPerformOperationException {
        User user = new User(firstName, lastName, password, email, birthday);
        users.save(user);
        return "redirect:/";
    }


}
