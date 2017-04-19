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

        result.put("name", space.getName());
        result.put("street-address", space.getStreetAddress());
        result.put("city", space.getCity());
        result.put("state", space.getState());
        result.put("cost-per-hour", space.getCostPerHour());
        result.put("square-feet", space.getSquareFeet());
        result.put("amenities", space.getAmenities());
        result.put("available-equipment", space.getAvailableEquipment());
        result.put("space-host-name", space.getSpaceHostName());
        result.put("host-email", space.getHostEmail());
        result.put("host-phone", space.getHostPhone());
        result.put("featured", space.getFeatured());
        result.put("description", space.getDescription());
        result.put("rules", space.getRules());
        result.put("coordinates", space.getCoordinates());
        result.put("user", space.getUser());

        return result;
    }

    public Map<String, String> getRelationshipUrls() {
        return new HashMap<String, String>() {{
            put("availabilities", "/spaces/{id}/availabilities");
        }};
    }
}
