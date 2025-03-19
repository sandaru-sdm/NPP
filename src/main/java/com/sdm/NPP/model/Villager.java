package com.sdm.NPP.model;

import com.google.cloud.firestore.annotation.DocumentId;

public class Villager {

    @DocumentId
    private String id;

    private String name;
    private String gender;
    private Address address;

    public Villager() {}

    public Villager(String name, String gender, Address address) {
        this.name = name;
        this.gender = gender;
        this.address = address;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
}
