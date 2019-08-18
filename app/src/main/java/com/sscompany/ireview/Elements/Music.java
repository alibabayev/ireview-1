package com.sscompany.ireview.Elements;

public class Music implements Item
{
    private String category = null;
    private String name = null;
    private String album = null;
    private String artist = null;
    private String genre = null;
    private String duration = null;
    private String language = null;
    private int thumbnail;
    private String parseItemId = null;

    public Music(String name, String album, String artist, String genre, String duration, String language)
    {
        setCategory();
        setTitle(name);
        setPublisher(artist);
        this.album = album;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
    }

    public Music(String name, String artist, String genre, String language)
    {
        setCategory();
        setTitle(name);
        setPublisher(artist);
        this.genre = genre;
        this.language = language;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory()
    {
        this.category = "music";
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setTitle(String title) {
        name = title;
    }

    @Override
    public int getThumbnail() {
        return thumbnail;
    }

    @Override
    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String getPublisher() {
        return artist;
    }

    @Override
    public void setPublisher(String publisher) {
        artist = publisher;
    }

    @Override
    public String getId() {
        return parseItemId;
    }

    @Override
    public void setId(String id) {
        parseItemId = id;
    }
}
