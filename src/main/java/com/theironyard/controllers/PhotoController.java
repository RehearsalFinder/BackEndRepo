package com.theironyard.controllers;

import java.io.File;
import java.util.Map;

import com.theironyard.entities.Photo;
import com.theironyard.entities.User;
import com.theironyard.parsers.RootParser;
import com.theironyard.serializers.PhotoPostSerializer;
import com.theironyard.serializers.RootSerializer;
import com.theironyard.services.PhotoRepository;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;


@RestController
@CrossOrigin(origins = "*")
public class PhotoController {

    PhotoPostSerializer photoPostSerializer;
    RootSerializer rootSerializer;

    @Autowired
    PhotoRepository photos;

    @Autowired
    UserRepository users;

    @Value("${cloud.aws.s3.bucket}")
    String bucket;

    @Autowired
    AmazonS3Client s3;


    @RequestMapping(path = "/photo-posts", method = RequestMethod.GET)
    public Map<String, Object> findAllPost() {
        Iterable<Photo> results = photos.findAll();

        return rootSerializer.serializeMany("/photo-posts", results, photoPostSerializer);
    }

    @RequestMapping(path = "/photo-posts", method = RequestMethod.POST)
    public Map<String, Object> storePost(@RequestBody RootParser<Photo> parser) {
        Photo photo = parser.getData().getEntity();
        photos.save(photo);

        return rootSerializer.serializeOne(
                "/photo-posts/" + photo.getId(),
                photo,
                photoPostSerializer);
    }

    @RequestMapping(path = "/user-profile/upload", method = RequestMethod.POST)
    public Map<String, Object> uploadPost(@RequestParam("photo") MultipartFile file)
            throws Exception {

        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        User user = users.findFirstByEmail(u.getName());

        // Creating a new Photo Entity
        Photo photo = new Photo();
        // Set properties other than the file
        photo.setUser(user);

        photo
                .setPhotoUrl("https://s3.amazonaws.com/" + bucket + "/" + file.getOriginalFilename());

        // Setup S3 request with bucket name, filename, file contents, and empty meta data
        PutObjectRequest s3Req = new PutObjectRequest(
                bucket,
                file.getOriginalFilename(),
                file.getInputStream(),
                new ObjectMetadata());

        // Save the object to s3
        s3.putObject(s3Req);

        photos.save(photo);

        return rootSerializer.serializeOne(
                "/photo-posts/" + photo.getId(),
                photo,
                photoPostSerializer);
    }

}