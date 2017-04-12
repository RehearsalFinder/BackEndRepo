package com.theironyard.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.entities.HasId;
import com.theironyard.entities.RehearsalSpace;
import com.theironyard.parsers.RootParser;
import com.theironyard.serializers.RootSerializer;
import com.theironyard.serializers.SpacesSerializer;
import com.theironyard.services.RehearsalSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            throws IOException {
        RehearsalSpace space = parser.getData().getEntity();
        spaces.save(space);
        response.setStatus(201);
        System.out.println(space.getDescription());
        return rootSerializer.serializeOne("/spaces", space, spacesSerializer);
    }

    @RequestMapping(path = "/spaces/{id}", method = RequestMethod.DELETE)
    public void deleteSpace(@PathVariable ("id") String id, HttpServletResponse response)
            throws IOException {
        RehearsalSpace deleteSpace = spaces.findFirstById(id);
        spaces.delete(deleteSpace);
        response.setStatus(204);
    }

    @RequestMapping(path = "/spaces/{id}", method = RequestMethod.PATCH)
    public Map<String, Object> updateSpaces(@PathVariable ("id") String id,
                                            @RequestBody RootParser<RehearsalSpace> parser) {
        RehearsalSpace existingSpaceInfo = spaces.findFirstById(id);
        RehearsalSpace newSpaceInfo = parser.getData().getEntity();
        existingSpaceInfo.setName(newSpaceInfo.getName());
        existingSpaceInfo.setLocation(newSpaceInfo.getLocation());
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
        spaces.save(existingSpaceInfo);

        return rootSerializer.serializeOne("/spaces/" + id,
                existingSpaceInfo, spacesSerializer);

    }

    @RequestMapping(path = "/spaces/{id}", method = RequestMethod.GET)
    public Map<String, Object> getSpace(@PathVariable("id") String id) throws Exception {
        //todo: try
        RehearsalSpace space = spaces.findFirstById(id);
        return rootSerializer.serializeOne("/spaces/" + id, space, spacesSerializer);
    }

}
