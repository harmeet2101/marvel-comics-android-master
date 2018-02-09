package com.segunfamisa.sample.comics.data.local.realm;


import io.realm.RealmObject;

/**
 * Realm table to store creator summary.
 */
public class CreatorSummaryRealm extends RealmObject {

    private String name;
    private String role;
    private String resourceUri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getResourceUri() {
        return resourceUri;
    }

    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }
}
