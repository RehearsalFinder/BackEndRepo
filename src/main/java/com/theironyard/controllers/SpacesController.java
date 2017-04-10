package com.theironyard.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.entities.RehearsalSpace;
import com.theironyard.entities.User;
import com.theironyard.parsers.RootParser;
import com.theironyard.serializers.RootSerializer;
import com.theironyard.serializers.SpacesSerializer;
import com.theironyard.services.RehearsalSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RestController
public class SpacesController {

    @Autowired
    RehearsalSpaceRepository spaces;

    RootSerializer rootSerializer = new RootSerializer();
    SpacesSerializer spacesSerializer = new SpacesSerializer();


//    @RequestMapping(path = "/browse-all", method = RequestMethod.GET)
//    public Map<String, Object> browseAll() {
//        ArrayList<RehearsalSpace> spacesList = (ArrayList<RehearsalSpace>) spaces.findAll();
//        return spacesList;
//    }

    @RequestMapping(path = "/add-space", method = RequestMethod.POST)
    public Map<String, Object> addSpace(@RequestBody RootParser<RehearsalSpace> parser, HttpServletResponse response)
            throws IOException {
        RehearsalSpace space = parser.getData().getEntity();
        spaces.save(space);
        response.setStatus(201);
        return rootSerializer.serializeOne("/add-space", space, spacesSerializer);
    }

    @RequestMapping(path = "/delete-space", method = RequestMethod.DELETE)
    public String deleteSpace(@RequestBody String body, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        RehearsalSpace selectedSpace = mapper.readValue(body, RehearsalSpace.class);
        String email = selectedSpace.getHostEmail();
        RehearsalSpace deleteSpace = spaces.findFirstByHostEmail(email);
        spaces.delete(deleteSpace);
        response.setStatus(201);
        return "Space removed";
    }

}
