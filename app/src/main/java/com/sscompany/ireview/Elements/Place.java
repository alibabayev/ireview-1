package com.sscompany.ireview.Elements;

public class Place implements InterfaceItem
{
    private String name;
    private String address;
    private String place_type;
    private String cover_photo;
    private String owner;

    public Place(String name, String address, String place_type, String cover_photo) {
        this.name = name;
        this.address = address;
        this.place_type = place_type;
        this.cover_photo = cover_photo;
        setOwner("");
    }

    public Place() {
        setOwner("");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace_type() {
        return place_type;
    }

    public void setPlace_type(String place_type) {
        this.place_type = place_type;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Place{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", place_type='" + place_type + '\'' +
                ", cover_photo='" + cover_photo + '\'' +
                '}';
    }
}
