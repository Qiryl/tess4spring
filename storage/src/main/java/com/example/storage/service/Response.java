package com.example.storage.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    public Response(Boolean status, String link) {
        this.status = status;
        this.link = link;
    }
    @JsonProperty
    private Boolean status;
    @JsonProperty
    private String link;
}
