package com.sscompany.ireview.Models;

/**
 * This class provides structure for uploading movies into firebase database.
 */

public class Movie implements InterfaceItem
{
    private String name;
    private String owner;
    private String genre;
    private String lead_actors;
    private String cover_photo;

    public Movie(String name, String owner, String genre, String lead_actors, String cover_photo) {
        this.name = name;
        this.owner = owner;
        this.genre = genre;
        this.lead_actors = lead_actors;
        this.cover_photo = cover_photo;
    }

    public Movie() {

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

    public String getLead_actors() {
        return lead_actors;
    }

    public void setLead_actors(String lead_actors) {
        this.lead_actors = lead_actors;
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
        return "Movie{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", genre='" + genre + '\'' +
                ", lead_actors='" + lead_actors + '\'' +
                ", cover_photo='" + cover_photo + '\'' +
                '}';
    }
}
