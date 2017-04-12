package com.theironyard.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.entities.User;
import com.theironyard.parsers.RootParser;
import com.theironyard.serializers.RootSerializer;
import com.theironyard.serializers.UserSerializer;
import com.theironyard.services.UserRepository;
import com.theironyard.utilities.JsonUser;
import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class UsersController {

    @Autowired
    UserRepository users;

    RootSerializer rootSerializer = new RootSerializer();
    UserSerializer userSerializer = new UserSerializer();

    @RequestMapping(path = "/add-users", method = RequestMethod.POST)
    public Map<String, Object> signUp(@RequestBody RootParser<User> parser, HttpServletResponse response) {
        User user = parser.getData().getEntity();
        String userEmail = user.getEmail();
        User user1 = users.findFirstByEmail(userEmail);
        if (user1 == null) {
            users.save(user);
            response.setStatus(201);
        } else try {
            throw new Exception("Email address is associated with an existing account!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootSerializer.serializeOne("/add-users", user, userSerializer);
    }


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody RootParser<JsonUser> parser, HttpServletResponse response)
            throws Exception {
        JsonUser jsonUser = parser.getData().getEntity();
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

    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public Map<String, Object> getUser(@PathVariable("id") String id) throws Exception {
        User user = users.findFirstById(id);
        return rootSerializer.serializeOne("/users/" + user.getId(), user, userSerializer);
    }

    @RequestMapping(path = "/delete-user", method = RequestMethod.DELETE)
    public Map<String, Object> deleteUser(@RequestBody RootParser<JsonUser> parser, HttpServletResponse response) throws Exception {
        JsonUser jsonUser = parser.getData().getEntity();
        String email = jsonUser.getEmail();
        String password = jsonUser.getPassword();
        User user = users.findFirstByEmail(email);
        if (users.findFirstByEmail(email) != null) {
            if (user.verifyPassword(password)) {
                users.delete(user);
                response.setStatus(204);
            } else throw new Exception("Wrong credentials!");
        }
        return rootSerializer.serializeOne("/delete-user", user, userSerializer);
    }

    @RequestMapping(path = "/delete-user/{id}", method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable("id") String id, HttpServletResponse response) {
        users.delete(id);
        response.setStatus(201);
    }

    @RequestMapping(path = "/update-users/{id}", method = RequestMethod.PATCH)
    public Map<String, Object> updateUsers(@PathVariable("id") String id, @RequestBody RootParser<User> parser) {
        User existingUserInfo = users.findOne(id);
        User newUserInfo = parser.getData().getEntity();
        existingUserInfo.setFirstName(newUserInfo.getFirstName());
        existingUserInfo.setLastName(newUserInfo.getLastName());
        existingUserInfo.setEmail(newUserInfo.getEmail());
        existingUserInfo.setBirthday(newUserInfo.getBirthday());
        existingUserInfo.setPhone(newUserInfo.getPhone());
        users.save(existingUserInfo);

        return rootSerializer.serializeOne("/update-users/" + existingUserInfo.getId(), existingUserInfo, userSerializer);
    }
    
}
