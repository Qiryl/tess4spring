package com.example.processor.repository;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaMessage {
    @JsonProperty
    private String id;
    @JsonProperty
    private String link;
    @JsonProperty
    private String type;
    @JsonProperty
    private String outType;

    public KafkaMessage() {}

    public KafkaMessage(String id, String link, String type, String outType) {
        this.id = id;
        this.link = link;
        this.type = type;
        this.outType = outType;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public String getLink() {
        return link;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setOutType(String outType) {
        this.outType = outType;
    }

    public String getOutType() {
        return outType;
    }
}
