package com.sscompany.ireview.Models;

/**
 * This class provides structure for uploading books into firebase database.
 */
public class Book implements InterfaceItem
{
    private String name;
    private String owner;
    private String genre;
    private String cover_photo;

    public Book(String name, String owner, String genre, String cover_photo) {
        this.name = name;
        this.owner = owner;
        this.genre = genre;
        this.cover_photo = cover_photo;
    }

    public Book() {

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", genre='" + genre + '\'' +
                ", cover_photo='" + cover_photo + '\'' +
                '}';
    }
}
