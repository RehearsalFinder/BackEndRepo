package com.theironyard.parsers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RootParser<T> {
    JsonApiDataParser<T> data;

    public RootParser(
            @JsonProperty("data") JsonApiDataParser<T> data) {
        this.data = data;
    }

    public JsonApiDataParser<T> getData() {
        return this.data;
    }
}
