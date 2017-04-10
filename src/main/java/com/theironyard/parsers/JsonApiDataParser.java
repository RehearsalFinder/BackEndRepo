package com.theironyard.parsers;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonApiDataParser<T> {
    String type;
    String id;
    T attributes;
    Map<String, RootParser> relationships;

    public JsonApiDataParser(
            @JsonProperty("type") String type,
            @JsonProperty("id") String id,
            @JsonProperty("attributes") T attributes,
            @JsonProperty("relationships") Map<String, RootParser> relationships
    ) {
        this.type = type;
        this.id = id;
        this.attributes = attributes;
        this.relationships = relationships;
    }

    public String getType() {
        return this.type;
    }

    public String getId() {
        return this.id;
    }

    public T getEntity() {
        return this.attributes;
    }

    public String getRelationshipId(String relationName) {
        return this.relationships.get(relationName).getData().getId();
    }
}