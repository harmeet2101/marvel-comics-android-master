package com.segunfamisa.sample.comics.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Creator Summary model/\.
 */
public class CreatorSummary {

    @SerializedName("name")
    private String name;

    @SerializedName("role")
    private String role;

    @SerializedName("resourceURI")
    private String resourceUri;

    public CreatorSummary() {
    }

    private CreatorSummary(String name, String role, String resourceUri) {
        this.name = name;
        this.role = role;
        this.resourceUri = resourceUri;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    /**
     * Builder class.
     */
    public static class Builder {
        private String name;
        private String role;
        private String resourceUri;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Builder setResourceUri(String resourceUri) {
            this.resourceUri = resourceUri;
            return this;
        }

        public CreatorSummary build() {
            return new CreatorSummary(name, role, resourceUri);
        }
    }
}
