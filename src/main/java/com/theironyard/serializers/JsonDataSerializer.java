package com.theironyard.serializers;

import com.theironyard.entities.HasId;
import java.util.Map;
import java.util.HashMap;


public abstract class JsonDataSerializer {
    abstract Map<String, Object> getAttributes(HasId data);

    abstract Map<String, String> getRelationshipUrls();

    abstract String getType();

    public Map<String, Object> serialize(HasId data) {
        Map<String, Object> result = new HashMap<>();

        result.put("type", this.getType());
        result.put("id", data.getId());
        result.put("attributes", this.getAttributes(data));

        return result;
    }
}
