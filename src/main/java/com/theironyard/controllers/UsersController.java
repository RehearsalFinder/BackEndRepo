package com.theironyard.controllers;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.theironyard.entities.Photo;
import com.theironyard.entities.User;
import com.theironyard.parsers.RootParser;
import com.theironyard.serializers.RootSerializer;
import com.theironyard.serializers.UserSerializer;
import com.theironyard.services.PhotoRepository;
import com.theironyard.services.UserRepository;
//import com.theironyard.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class UsersController {

    @Autowired
    UserRepository users;

    @Autowired
    AmazonS3Client s3;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PhotoRepository photos;

    @Value("${cloud.aws.s3.bucket}")
    String bucket;

    RootSerializer rootSerializer = new RootSerializer();
    UserSerializer userSerializer = new UserSerializer();

    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public Map<String, Object> create(
//            @RequestParam("photo") MultipartFile file,
//                                      @RequestParam("first-name") String firstName,
//                                      @RequestParam("last-name") String lastName,
//                                      @RequestParam("email") String email,
//                                      @RequestParam("birthday") String birthday,
//                                      @RequestParam("phone") String phone,
//                                      @RequestParam("password") String password,
                                         HttpServletResponse response,
                                        @RequestBody RootParser<User> parser) {

            User user = parser.getData().getEntity();
//            User user = new User();
//        user.setEmail(email);
        String userEmail = user.getEmail();
        User user1 = users.findFirstByEmail(userEmail);
        if (user1 == null) {
//            user.setFirstName(firstName);
//            user.setLastName(lastName);
//            user.setEmail(email);
//            user.setBirthday(birthday);
//            user.setPhone(phone);
//            user.setPassword(password);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

//            Photo photo = new Photo();
//            photo.setUser(user);

//            user
//                    .setPhotoUrl("https://s3.amazonaws.com/" + bucket + "/" + file.getOriginalFilename());
//
//            try {
//                PutObjectRequest s3Req = new PutObjectRequest(
//                        bucket,
//                        file.getOriginalFilename(),
//                        file.getInputStream(),
//                        new ObjectMetadata());
//                s3.putObject(s3Req);
//                photos.save(photo);
//            } catch (Exception e) {
//                e.getMessage();
//            }

            users.save(user);
            response.setStatus(201);
            try {
                throw new Exception("Email address is associated with an existing account!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return rootSerializer.serializeOne("/users", user, userSerializer);
    }

    @RequestMapping(path = "/users/current", method = RequestMethod.GET)
    public Map<String, Object> currentUser() {
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        String email = u.getName();
        User user = users.findFirstByEmail(email);
        return rootSerializer.serializeOne("/users", user, userSerializer);
    }


    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public Map<String, Object> getUser(@PathVariable("id") String id) {
        User user = new User();
        try {
            user = users.findFirstById(id);
        } catch (Exception e) {
            e.getMessage();
        }
        return rootSerializer.serializeOne("/users/" + id, user, userSerializer);
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id, HttpServletResponse response) {
        try {
            users.delete(id);
            response.setStatus(201);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @RequestMapping(path = "/users/{id}", method = RequestMethod.PATCH)
    public Map<String, Object> update(@PathVariable("id") String id, @RequestBody RootParser<User> parser) {
        User existingUserInfo = new User();
        try {
            existingUserInfo = users.findOne(id);
            User newUserInfo = parser.getData().getEntity();
            existingUserInfo.setFirstName(newUserInfo.getFirstName());
            existingUserInfo.setLastName(newUserInfo.getLastName());
            existingUserInfo.setEmail(newUserInfo.getEmail());
            existingUserInfo.setBirthday(newUserInfo.getBirthday());
            existingUserInfo.setPhone(newUserInfo.getPhone());
            users.save(existingUserInfo);
        } catch (Exception e) {
            e.getMessage();
        }

        return rootSerializer.serializeOne("/users/" + existingUserInfo.getId(), existingUserInfo, userSerializer);
    }

}
