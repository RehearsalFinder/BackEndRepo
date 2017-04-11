package com.theironyard.serializers;

import com.theironyard.entities.HasId;
import com.theironyard.entities.User;

import java.util.HashMap;
import java.util.Map;


public class UserSerializer extends JsonDataSerializer {

    public String getType() {
        return "users";
    }

    public Map<String, Object> getAttributes(HasId entity) {
        Map<String, Object> result = new HashMap<>();
        User user = (User) entity;

        result.put("id", user.getId());
        result.put("firstName", user.getFirstName());
        result.put("lastName", user.getLastName());
        result.put("birthday", user.getBirthday());
        result.put("email", user.getEmail());
        result.put("phone", user.getPhone());

        return result;
    }

    public Map<String, String> getRelationshipUrls() {
        return new HashMap<String, String>();
    }
}
