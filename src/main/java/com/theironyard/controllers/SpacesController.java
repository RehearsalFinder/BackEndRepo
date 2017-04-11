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
public class SpacesController {

    @Autowired
    RehearsalSpaceRepository spaces;

    RootSerializer rootSerializer = new RootSerializer();
    SpacesSerializer spacesSerializer = new SpacesSerializer();


    @RequestMapping(path = "/browse-all", method = RequestMethod.GET)
    public Map<String, Object> browseAll() {
        Iterable<RehearsalSpace> spacesList = spaces.findAll();
        return rootSerializer.serializeMany("/browse-all", spacesList, spacesSerializer);
    }

    @RequestMapping(path = "/add-space", method = RequestMethod.POST)
    public Map<String, Object> addSpace(@RequestBody RootParser<RehearsalSpace> parser, HttpServletResponse response)
            throws IOException {
        RehearsalSpace space = parser.getData().getEntity();
        spaces.save(space);
        response.setStatus(201);
        return rootSerializer.serializeOne("/add-space", space, spacesSerializer);
    }

    @RequestMapping(path = "/delete-space/{id}", method = RequestMethod.DELETE)
    public void deleteSpace(@PathVariable ("id") String id, HttpServletResponse response)
            throws IOException {
        RehearsalSpace deleteSpace = spaces.findFirstById(id);
        spaces.delete(deleteSpace);
        response.setStatus(201);
    }

}
