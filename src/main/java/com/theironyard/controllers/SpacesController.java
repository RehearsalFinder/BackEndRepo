package com.theironyard.controllers;

import com.theironyard.entities.RehearsalSpace;
import com.theironyard.parsers.RootParser;
import com.theironyard.serializers.RootSerializer;
import com.theironyard.serializers.SpacesSerializer;
import com.theironyard.services.RehearsalSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            spaces.save(space);
            response.setStatus(201);
        } catch (Exception e) {
            e.getMessage();
        }
        System.out.println(space.getDescription());
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

}
