package com.sscompany.ireview.Models;

/**
 * This class provides structure for uploading games into firebase database.
 */
public class Game implements InterfaceItem
{
    private String name;
    private String owner;
    private String game_type;
    private String cover_photo;

    public Game(String name, String owner, String game_type, String cover_photo) {
        this.name = name;
        this.owner = owner;
        this.game_type = game_type;
        this.cover_photo = cover_photo;
    }

    public Game() {

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

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", game_type='" + game_type + '\'' +
                ", cover_photo='" + cover_photo + '\'' +
                '}';
    }
}
