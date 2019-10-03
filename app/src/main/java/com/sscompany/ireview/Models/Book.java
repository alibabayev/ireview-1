package com.sscompany.ireview.Models;

/**
 * This class provides structure for uploading books into firebase database.
 */
public class Book implements InterfaceItem
{
    private String name;
    private String type;
    private String owner;
    private String detail;
    private String cover_photo;
    private String category;

    public Book(String name, String genre, String author, String cover_photo)
    {
        this.name = name;
        this.type = genre;
        this.owner = author;
        this.detail = "";
        this.cover_photo = cover_photo;
        setCategory("Books");
    }

    public Book() {
        this.detail = "";
        setCategory("Books");
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
    public void setType(String genre) {
        this.type = genre;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public void setOwner(String author) {
        this.owner = author;
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
        return "Book{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", owner='" + owner + '\'' +
                ", detail='" + detail + '\'' +
                ", cover_photo='" + cover_photo + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
