package com.sscompany.ireview.Models;

/**
 * This class provides structure for uploading items into firebase database.
 */
public class Item
{
    private String category;
    private String item_id;
    private String name;
    private String type;
    private String owner;
    private String detail;
    private String cover_photo;

    public Item(String category, String item_id, String name, String type, String owner, String detail, String cover_photo)
    {
        this.category = category;
        this.item_id = item_id;
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.detail = detail;
        this.cover_photo = cover_photo;
    }

    public Item() {

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    @Override
    public String toString()
    {
        return "Item{" +
                "category='" + category + '\'' +
                ", item_id='" + item_id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", owner='" + owner + '\'' +
                ", detail='" + detail + '\'' +
                ", cover_photo='" + cover_photo + '\'' +
                '}';
    }
}
