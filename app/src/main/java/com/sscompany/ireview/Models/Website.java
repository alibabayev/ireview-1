package com.sscompany.ireview.Models;

/**
 * This class provides structure for uploading websites into firebase database.
 */

public class Website implements InterfaceItem
{
    private String name;
    private String type;
    private String owner;
    private String detail;
    private String cover_photo;
    private String category;

    public Website(String title, String use, String company, String http, String cover_photo)
    {
        this.name = title;
        this.type = use;
        this.owner = company;
        this.detail = http;
        this.cover_photo = cover_photo;
        setCategory("Websites");
    }

    public Website()
    {
        setCategory("Websites");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String getDetail() {
        return detail;
    }

    @Override
    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String getCover_photo() {
        return cover_photo;
    }

    @Override
    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Website{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", owner='" + owner + '\'' +
                ", detail='" + detail + '\'' +
                ", cover_photo='" + cover_photo + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
