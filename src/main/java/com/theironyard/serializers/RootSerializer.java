package com.theironyard.serializers;

import com.theironyard.entities.HasId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RootSerializer {

    private HashMap<String, Object> makeRoot(String resourceUrl) {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> links = new HashMap<>();

        links.put("self", resourceUrl);
        result.put("links", links);

        return result;
    }

    public HashMap<String, Object> serializeOne(String resourceUrl, HasId data, JsonDataSerializer serializer) {
        HashMap<String, Object> result = makeRoot(resourceUrl);
        result.put("data", serializer.serialize(data));

        return result;
    }

    public HashMap<String, Object> serializeMany(String resourceUrl, List<HasId> data, JsonDataSerializer serializer) {
        HashMap<String, Object> result = makeRoot(resourceUrl);
        result.put("data", data.stream().map((e) -> serializer.serialize(e)).collect(Collectors.toList()));

        return result;
    }

    public HashMap<String, Object> serializeMany(String resourceUrl, Iterable data, JsonDataSerializer serializer) {
        Iterable<HasId> results = (Iterable<HasId>) data;
        List<HasId> resultsList = new ArrayList<>();

        results.iterator().forEachRemaining(resultsList::add);

        return this.serializeMany(resourceUrl, resultsList, serializer);
    }

}

