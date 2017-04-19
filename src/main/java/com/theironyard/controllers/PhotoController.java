package com.theironyard.controllers;

import java.io.File;
import java.util.Map;

import com.theironyard.entities.Photo;
import com.theironyard.entities.RehearsalSpace;
import com.theironyard.entities.User;
import com.theironyard.parsers.RootParser;
import com.theironyard.serializers.PhotoPostSerializer;
import com.theironyard.serializers.RootSerializer;
import com.theironyard.services.PhotoRepository;
import com.theironyard.services.RehearsalSpaceRepository;
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

    PhotoPostSerializer photoPostSerializer = new PhotoPostSerializer();
    RootSerializer rootSerializer = new RootSerializer();

    @Autowired
    PhotoRepository photos;

    @Autowired
    UserRepository users;

    @Autowired
    RehearsalSpaceRepository spaces;

    @Value("${cloud.aws.s3.bucket}")
    String bucket;

    @Autowired
    AmazonS3Client s3;


    @RequestMapping(path = "/images", method = RequestMethod.GET)
    public Map<String, Object> findAllPost() {
        Iterable<Photo> results = photos.findAll();

        return rootSerializer.serializeMany("/photo-posts", results, photoPostSerializer);
    }

    @RequestMapping(path = "/photo-posts", method = RequestMethod.POST)
    public Map<String, Object> storePost(@RequestBody RootParser<Photo> parser) {
        Photo photo = new Photo();
        try {
            photo = parser.getData().getEntity();
            photos.save(photo);
        } catch (Exception e) {
            e.getMessage();
        }
        return rootSerializer.serializeOne(
                "/photo-posts/" + photo.getId(),
                photo,
                photoPostSerializer);
    }

    @RequestMapping(path = "/images/users/upload", method = RequestMethod.POST)
    public Map<String, Object> uploadUserImage(@RequestParam("photo") MultipartFile file)
             {
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        User user = users.findFirstByEmail(u.getName());
        Photo photo = new Photo();
        photo.setUser(user);

        photo
                .setPhotoUrl("https://s3.amazonaws.com/" + bucket + "/" + file.getOriginalFilename());

        try {
            PutObjectRequest s3Req = new PutObjectRequest(
                    bucket,
                    file.getOriginalFilename(),
                    file.getInputStream(),
                    new ObjectMetadata());
            s3.putObject(s3Req);
            photos.save(photo);
        } catch (Exception e) {
            e.getMessage();
        }
        return rootSerializer.serializeOne(
                "/photo-posts/" + photo.getId(),
                photo,
                photoPostSerializer);
    }

    @RequestMapping(path = "/images/spaces/{id}/upload", method = RequestMethod.POST)
    public Map<String, Object> uploadSpaceImage(@RequestParam("photo") MultipartFile file,
                                                @PathVariable("id") String id)
    {
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        User user = users.findFirstByEmail(u.getName());
        Photo photo = new Photo();
        photo.setUser(user);

        RehearsalSpace space = spaces.findFirstById(id);
        photo.setSpace(space);

        photo
                .setPhotoUrl("https://s3.amazonaws.com/" + bucket + "/" + file.getOriginalFilename());

        try {
            PutObjectRequest s3Req = new PutObjectRequest(
                    bucket,
                    file.getOriginalFilename(),
                    file.getInputStream(),
                    new ObjectMetadata());
            s3.putObject(s3Req);
            photos.save(photo);
        } catch (Exception e) {
            e.getMessage();
        }
        return rootSerializer.serializeOne(
                "/photo-posts/" + photo.getId(),
                photo,
                photoPostSerializer);
    }

}