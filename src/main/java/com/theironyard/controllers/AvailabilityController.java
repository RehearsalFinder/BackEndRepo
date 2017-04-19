package com.theironyard.controllers;


import com.theironyard.entities.Availability;
import com.theironyard.entities.RehearsalSpace;
import com.theironyard.entities.User;
import com.theironyard.parsers.RootParser;
import com.theironyard.serializers.AvailabilitySerializer;
import com.theironyard.serializers.RootSerializer;
import com.theironyard.services.AvailabilityRepository;
import com.theironyard.services.RehearsalSpaceRepository;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class AvailabilityController {

    RootSerializer rootSerializer = new RootSerializer();
    AvailabilitySerializer availabilitySerializer = new AvailabilitySerializer();

    @Autowired
    AvailabilityRepository availabilityRepository;

    @Autowired
    RehearsalSpaceRepository spaces;

    @Autowired
    UserRepository users;


    @RequestMapping(path = "/availabilities", method = RequestMethod.POST)
    public Map<String, Object> createAvailability(@RequestBody RootParser<Availability> parser) {
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        String email = u.getName();
        User user = users.findFirstByEmail(email);

        RehearsalSpace space = spaces.findFirstById(parser.getData().getRelationshipId("space"));

        Availability availability = parser.getData().getEntity();
        if (user != null) {
            try {
                availability.setClaimedBy(user);
                availability.setBelongsTo(space);
                availabilityRepository.save(availability);
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return rootSerializer.serializeOne("/availabilities/" + availability.getId(),
                availability, availabilitySerializer);
    }

    @RequestMapping(path = "/spaces/{id}/availabilities", method = RequestMethod.GET)
    public Map<String, Object> getAvailabilitiesBySpace(@PathVariable("id") String id) {
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        String email = u.getName();
        User user = users.findFirstByEmail(email);
        ArrayList<Availability> availList = new ArrayList<>();
        if (user != null) {
            RehearsalSpace space = spaces.findFirstById(id);
            availList = availabilityRepository.findAllByBelongsTo(space);
        }
        return rootSerializer.serializeMany("/spaces/" + id + "/availabilities",
                availList, availabilitySerializer);
    }

    @RequestMapping(path = "/users/{id}/availabilities", method = RequestMethod.GET)
    public Map<String, Object> getUsersAvailabilities(@PathVariable("id") String id) {
        User user = users.findFirstById(id);
        ArrayList<Availability> userAvailList = availabilityRepository.findAllByClaimedBy(user) ;
        return rootSerializer.serializeMany("/users/" + id + "/availabilities",
                userAvailList, availabilitySerializer);
    }

    @RequestMapping(path = "/availabilities/{id}", method = RequestMethod.DELETE)
    public void deleteAvailability(@PathVariable("id") String id) {
        Availability deleteAvailability = availabilityRepository.findFirstById(id);
        try {
            System.out.println(deleteAvailability);
            availabilityRepository.delete(deleteAvailability);
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @RequestMapping(path = "/availabilities/{id}", method = RequestMethod.PATCH)
    public Map<String, Object> updateAvailability(@PathVariable("id") String id,
                                                  @RequestBody RootParser<Availability> parser) {

        Availability existingAvailabilityInfo = new Availability();
        Availability newAvailabilityInfo = parser.getData().getEntity();

        existingAvailabilityInfo = availabilityRepository.findFirstById(id);

        existingAvailabilityInfo.setDate(newAvailabilityInfo.getDate());
        existingAvailabilityInfo.setBelongsTo(newAvailabilityInfo.getBelongsTo());
        existingAvailabilityInfo.setClaimedBy(newAvailabilityInfo.getClaimedBy());
        existingAvailabilityInfo.setEndTime(newAvailabilityInfo.getEndTime());
        existingAvailabilityInfo.setStartTime(newAvailabilityInfo.getStartTime());

        try {
            availabilityRepository.save(existingAvailabilityInfo);
        } catch (Exception e) {
            e.getMessage();
        }
        return rootSerializer.serializeOne("/availabilities/" + id,
                existingAvailabilityInfo, availabilitySerializer);
    }


}
