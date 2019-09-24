package com.sscompany.ireview.Models;

/**
 * This class provides structure for uploading musics into firebase database.
 */

public class Music implements InterfaceItem
{
    private String name;
    private String owner;
    private String genre;
    private String language;
    private String cover_photo;

    public Music(String name, String owner, String genre, String language, String cover_photo) {
        this.name = name;
        this.owner = owner;
        this.genre = genre;
        this.language = language;
        this.cover_photo = cover_photo;
    }

    public Music() {

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
    public String getOwner() {
        return owner;
    }

    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
    public String toString() {
        return "Music{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", genre='" + genre + '\'' +
                ", language='" + language + '\'' +
                ", cover_photo='" + cover_photo + '\'' +
                '}';
    }
}
