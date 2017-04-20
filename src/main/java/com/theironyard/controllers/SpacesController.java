package com.theironyard.controllers;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.theironyard.entities.Photo;
import com.theironyard.entities.User;
import com.theironyard.google_geocode_entities.Geocode;
import com.theironyard.entities.RehearsalSpace;
import com.theironyard.parsers.RootParser;
import com.theironyard.serializers.RootSerializer;
import com.theironyard.serializers.SpacesSerializer;
import com.theironyard.services.PhotoRepository;
import com.theironyard.services.RehearsalSpaceRepository;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
public class SpacesController {

    @Autowired
    RehearsalSpaceRepository spaces;

    @Autowired
    UserRepository users;

    @Autowired
    AmazonS3Client s3;

    @Autowired
    PhotoRepository photos;

    RootSerializer rootSerializer = new RootSerializer();
    SpacesSerializer spacesSerializer = new SpacesSerializer();

    @Value("${cloud.aws.s3.bucket}")
    String bucket;


    @RequestMapping(path = "/spaces", method = RequestMethod.GET)
    public Map<String, Object> getAll() {
        Iterable<RehearsalSpace> spacesList = spaces.findAll();
        return rootSerializer.serializeMany("/spaces", spacesList, spacesSerializer);
    }

    @RequestMapping(path = "/spaces/upload", method = RequestMethod.POST)
    public Map<String, Object> createSpace(@RequestParam("photo") MultipartFile file,
                                           @RequestParam("name") String name,
                                           @RequestParam("street-address") String streetAddress,
                                           @RequestParam("city") String city,
                                           @RequestParam("state") String state,
                                           @RequestParam("zip") String zip,
                                           @RequestParam("cost-per-hour") String costPerHour,
                                           @RequestParam("square-feet") String squareFeet,
                                           @RequestParam("amenities") ArrayList<String> amenities,
                                           @RequestParam("available-equipment") ArrayList<String> availableEquipment,
                                           @RequestParam("space-host-name") String spaceHostName,
                                           @RequestParam("host-email") String hostEmail,
                                           @RequestParam("host-phone") String hostPhone,
                                           @RequestParam("featured") String featured,
                                           @RequestParam("description") String description,
                                           @RequestParam("rules") String rules,
                                           HttpServletResponse response) {
        RehearsalSpace space = new RehearsalSpace();
        space.setName(name);
        space.setStreetAddress(streetAddress);
        space.setCity(city);
        space.setState(state);
        space.setZip(zip);
        space.setCostPerHour(costPerHour);
        space.setSquareFeet(squareFeet);
        space.setAmenities(amenities);
        space.setAvailableEquipment(availableEquipment);
        space.setSpaceHostName(spaceHostName);
        space.setHostEmail(hostEmail);
        space.setHostPhone(hostPhone);
        space.setFeatured(featured);
        space.setDescription(description);
        space.setRules(rules);

        String spaceName = space.getName();
        RehearsalSpace checkSpace = spaces.findFirstByName(spaceName);

        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        User user = users.findFirstByEmail(u.getName());
//        Photo photo = new Photo();
//        photo.setUser(user);

        if (checkSpace == null) {
            try {
                ArrayList<Double> coordinates = getGeocode(streetAddress, city, state, zip);
                space.setCoordinates(coordinates);
                space.setUser(user);
//                photo.setSpace(space);



                try {
                    PutObjectRequest s3Req = new PutObjectRequest(
                            bucket,
                            file.getOriginalFilename(),
                            file.getInputStream(),
                            new ObjectMetadata());
                    s3.putObject(s3Req);

                    space
                            .setPhotoUrl("https://s3.amazonaws.com/" + bucket + "/" + file.getOriginalFilename());
                } catch (Exception e) {
                    e.getMessage();
                }

                spaces.save(space);
                response.setStatus(201);

            } catch (Exception e) {
                e.getMessage();
            }
        } else {
            try {
                response.sendError(400, "Rehearsal space name already exists!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rootSerializer.serializeOne("/spaces", space, spacesSerializer);
    }

    @RequestMapping(path = "/spaces/{id}", method = RequestMethod.DELETE)
    public void deleteSpace(@PathVariable("id") String id, HttpServletResponse response) {
        RehearsalSpace deleteSpace = new RehearsalSpace();
        try {
            deleteSpace = spaces.findFirstById(id);
        } catch (Exception e) {
            e.getMessage();
        }
        spaces.delete(deleteSpace);
        response.setStatus(204);
    }

    @RequestMapping(path = "/spaces/{id}", method = RequestMethod.PATCH)
    public Map<String, Object> updateSpaces(@PathVariable("id") String id,
                                            @RequestBody RootParser<RehearsalSpace> parser) {
        RehearsalSpace newSpaceInfo = parser.getData().getEntity();
        RehearsalSpace existingSpaceInfo = spaces.findFirstById(id);

        existingSpaceInfo.setName(newSpaceInfo.getName());
        existingSpaceInfo.setStreetAddress(newSpaceInfo.getStreetAddress());
        existingSpaceInfo.setCity(newSpaceInfo.getCity());
        existingSpaceInfo.setState(newSpaceInfo.getState());
        existingSpaceInfo.setZip(newSpaceInfo.getZip());

        ArrayList<Double> coordinates = getGeocode(existingSpaceInfo.getStreetAddress(),
                existingSpaceInfo.getCity(), existingSpaceInfo.getState(), existingSpaceInfo.getZip());

        existingSpaceInfo.setCoordinates(coordinates);

        existingSpaceInfo.setSpaceHostName(newSpaceInfo.getName());
        existingSpaceInfo.setAmenities(newSpaceInfo.getAmenities());
        existingSpaceInfo.setAvailableEquipment(newSpaceInfo.getAvailableEquipment());
        existingSpaceInfo.setCostPerHour(newSpaceInfo.getCostPerHour());
        existingSpaceInfo.setFeatured(newSpaceInfo.getFeatured());
        existingSpaceInfo.setSquareFeet(newSpaceInfo.getSquareFeet());
        existingSpaceInfo.setHostPhone(newSpaceInfo.getHostPhone());
        existingSpaceInfo.setHostEmail(newSpaceInfo.getHostEmail());
        existingSpaceInfo.setDescription(newSpaceInfo.getDescription());
        existingSpaceInfo.setRules(newSpaceInfo.getRules());

        existingSpaceInfo.setSpaceHostName(newSpaceInfo.getSpaceHostName());

        try {
            spaces.save(existingSpaceInfo);
        } catch (Exception e) {
            e.getMessage();
        }

        return rootSerializer.serializeOne("/spaces/" + id,
                existingSpaceInfo, spacesSerializer);

    }

    @RequestMapping(path = "/spaces/{id}", method = RequestMethod.GET)
    public Map<String, Object> getSpace(@PathVariable("id") String id, HttpServletResponse response) {
        RehearsalSpace space = new RehearsalSpace();
        try {
            space = spaces.findFirstById(id);
            response.setStatus(200);
        } catch (Exception e) {
            e.getMessage();
        }
        return rootSerializer.serializeOne("/spaces/" + id, space, spacesSerializer);
    }

    @RequestMapping(path = "/spaces/featured", method = RequestMethod.GET)
    public Map<String, Object> getFeatured(HttpServletResponse response) {
        ArrayList<RehearsalSpace> featuredList = new ArrayList<>();
        try {
            featuredList = spaces.findAllByFeaturedEquals("yes");
            response.setStatus(200);
        } catch (Exception e) {
            e.getMessage();
        }
        return rootSerializer.serializeMany("/spaces/featured", featuredList, spacesSerializer);
    }

    public ArrayList<Double> getGeocode(String streetAddress, String city, String state, String zip) {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://maps.googleapis.com/maps/api/geocode/" +
                "json?address=" + streetAddress + ",+" + city + ",+" + state +
                "&key=AIzaSyCQTsAqb_RkAP84Ph9dSHT1cFZNZV6JzPo";
        RestTemplate template = new RestTemplate();
        ResponseEntity<Geocode> geocode = template.exchange(url, HttpMethod.GET, entity, Geocode.class);
        Double lat = Double.valueOf(geocode.getBody().getLat());
        Double lng = Double.valueOf(geocode.getBody().getLng());
        ArrayList <Double> coordinates = new ArrayList<>();
        coordinates.add(0, lat);
        coordinates.add(1, lng);
//        String coordinates = lat + ", " + lng;

        return coordinates;
    }

}
