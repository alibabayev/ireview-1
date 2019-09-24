package com.sscompany.ireview.Models;

/**
 * This class provides structure for uploading websites into firebase database.
 */

public class Website implements InterfaceItem
{
    private String name;
    private String owner;
    private String use;
    private String cover_photo;

    public Website(String name, String use, String cover_photo) {
        this.name = name;
        this.use = use;
        this.cover_photo = cover_photo;
        setOwner("none");
    }

    public Website() {
        setOwner("none");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    @Override
    public String toString() {
        return "Website{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", use='" + use + '\'' +
                ", cover_photo='" + cover_photo + '\'' +
                '}';
    }
}
