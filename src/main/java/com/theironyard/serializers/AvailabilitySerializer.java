package com.theironyard.serializers;

import com.theironyard.entities.Availability;
import com.theironyard.entities.HasId;

import java.util.HashMap;
import java.util.Map;


public class AvailabilitySerializer extends JsonDataSerializer {

    public String getType() {
        return "availabilities";
    }

    public Map<String, Object> getAttributes(HasId entity) {
        Map<String, Object> result = new HashMap<>();
        Availability availability = (Availability) entity;

        result.put("date", availability.getDate());
        result.put("start-time", availability.getStartTime());
        result.put("end-time", availability.getEndTime());
        result.put("claimed-by", availability.getClaimedBy());
        result.put("belongs-to", availability.getBelongsTo());
        return result;
    }

    public Map<String, String> getRelationshipUrls() {
        return new HashMap<String, String>();
    }
}
