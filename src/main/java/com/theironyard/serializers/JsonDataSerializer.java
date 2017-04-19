package com.theironyard.serializers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theironyard.entities.HasId;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;


public abstract class JsonDataSerializer {
    abstract Map<String, Object> getAttributes(HasId data);

    abstract Map<String, String> getRelationshipUrls();

    private Map<String, Object> getRelationships(String id) {
        return this.getRelationshipUrls()
                .entrySet().stream()
                .collect(Collectors.toMap((entry) -> entry.getKey(),
                        (entry) -> {
                            Map<String, Object> result = new HashMap<>();
                            Map<String, Object> links = new HashMap<>();
                            String url = entry.getValue();

                            links.put("related", url.replace("{id}", id));

                            result.put("links", links);

                            return result;
                        }));
    }

    abstract String getType();

    public Map<String, Object> serialize(HasId data) {
        Map<String, Object> result = new HashMap<>();

        result.put("type", this.getType());
        result.put("id", data.getId());
        result.put("attributes", this.getAttributes(data));
        result.put("relationships", this.getRelationships(data.getId()));

        return result;
    }
}
