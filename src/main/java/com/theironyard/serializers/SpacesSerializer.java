package com.theironyard.serializers;

import com.theironyard.entities.HasId;
import com.theironyard.entities.RehearsalSpace;

import java.util.HashMap;
import java.util.Map;


public class SpacesSerializer extends JsonDataSerializer {

    public String getType() {
        return "spaces";
    }

    public Map<String, Object> getAttributes(HasId entity) {
        Map<String, Object> result = new HashMap<>();
        RehearsalSpace space = (RehearsalSpace) entity;

        result.put("id", space.getId());
        result.put("name", space.getName());
        result.put("location", space.getLocation());
        result.put("costPerHour", space.getCostPerHour());
        result.put("squareFeet", space.getSquareFeet());
        result.put("amenities", space.getAmenities());
        result.put("availableEquipment", space.getAvailableEquipment());
        result.put("spaceHostName", space.getSpaceHostName());
        result.put("hostEmail", space.getHostEmail());
        result.put("hostPhone", space.getHostPhone());
        result.put("featured", space.getFeatured());
        result.put("description", space.getDescription());
        result.put("rules", space.getRules());


        return result;
    }

    public Map<String, String> getRelationshipUrls() {
        return new HashMap<String, String>();
    }
}
