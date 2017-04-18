package com.theironyard.controllers;

import com.theironyard.google_geocode_entities.Geocode;
import com.theironyard.entities.RehearsalSpace;
import com.theironyard.parsers.RootParser;
import com.theironyard.serializers.RootSerializer;
import com.theironyard.serializers.SpacesSerializer;
import com.theironyard.services.RehearsalSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
public class SpacesController {

    @Autowired
    RehearsalSpaceRepository spaces;

    RootSerializer rootSerializer = new RootSerializer();
    SpacesSerializer spacesSerializer = new SpacesSerializer();


    @RequestMapping(path = "/spaces", method = RequestMethod.GET)
    public Map<String, Object> getAll() {
        Iterable<RehearsalSpace> spacesList = spaces.findAll();
        return rootSerializer.serializeMany("/spaces", spacesList, spacesSerializer);
    }

    @RequestMapping(path = "/spaces", method = RequestMethod.POST)
    public Map<String, Object> createSpace(@RequestBody RootParser<RehearsalSpace> parser, HttpServletResponse response)
            {
        RehearsalSpace space = parser.getData().getEntity();
        try {
            String streetAddress = space.getStreetAddress();
            String city = space.getCity();
            String state = space.getState();
            String zip = space.getZip();
            String coordinates = getGeocode(streetAddress, city, state, zip);
            space.setCoordinates(coordinates);
            spaces.save(space);
            response.setStatus(201);

        } catch (Exception e) {
            e.getMessage();
        }
        return rootSerializer.serializeOne("/spaces", space, spacesSerializer);
    }

    @RequestMapping(path = "/spaces/{id}", method = RequestMethod.DELETE)
    public void deleteSpace(@PathVariable ("id") String id, HttpServletResponse response)
            {
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
    public Map<String, Object> updateSpaces(@PathVariable ("id") String id,
                                            @RequestBody RootParser<RehearsalSpace> parser) {
        RehearsalSpace existingSpaceInfo = new RehearsalSpace();
        RehearsalSpace newSpaceInfo = parser.getData().getEntity();

        try {
            existingSpaceInfo = spaces.findFirstById(id);
        } catch (Exception e) {
            e.getMessage();
        }

        existingSpaceInfo.setName(newSpaceInfo.getName());
        existingSpaceInfo.setStreetAddress(newSpaceInfo.getStreetAddress());
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
        existingSpaceInfo.setCity(newSpaceInfo.getCity());
        existingSpaceInfo.setState(newSpaceInfo.getState());
        existingSpaceInfo.setZip(newSpaceInfo.getZip());

        try {
            spaces.save(existingSpaceInfo);
        } catch (Exception e) {
            e.getMessage();
        }

        return rootSerializer.serializeOne("/spaces/" + id,
                existingSpaceInfo, spacesSerializer);

    }

    @RequestMapping(path = "/spaces/{id}", method = RequestMethod.GET)
    public Map<String, Object> getSpace(@PathVariable("id") String id, HttpServletResponse response){
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

    public String getGeocode(String streetAddress, String city, String state, String zip) {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = "https://maps.googleapis.com/maps/api/geocode/" +
                "json?address=" + streetAddress + ",+" + city + ",+" + state +
                "&key=AIzaSyCQTsAqb_RkAP84Ph9dSHT1cFZNZV6JzPo";
        RestTemplate template = new RestTemplate();
        ResponseEntity<Geocode> geocode = template.exchange(url, HttpMethod.GET, entity, Geocode.class);
        String lat = geocode.getBody().getLat();
        String lng = geocode.getBody().getLng();
        String coordinates = lat + ", " + lng;

        return coordinates;
    }

}
