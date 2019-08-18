package com.sscompany.ireview.Elements;

public class TVShow implements Item
{
    private String category = null;
    private String name = null;
    private String director = null;
    private String host = null;
    private int year;
    private String country = null;
    private String genre = null;
    private String episodeDuration = null;
    private int seasons;
    private String favoriteEpisode = null;
    private int thumbnail;
    private String parseItemId = null;

    public TVShow(String name, String director, String host, int year, String country, String genre, String episodeDuration, int seasons, String favoriteEpisode)
    {
        setCategory();
        setTitle(name);
        setPublisher(host);
        this.director = director;
        this.year = year;
        this.genre = genre;
        this.episodeDuration = episodeDuration;
        this.country = country;
        this.seasons = seasons;
        this.favoriteEpisode = favoriteEpisode;
    }

    public TVShow(String name, String host, String genre, String favoriteEpisode)
    {
        setCategory();
        setTitle(name);
        setPublisher(host);
        this.genre = genre;
        this.favoriteEpisode = favoriteEpisode;
    }

    public TVShow(String name, String genre, String favoriteEpisode, int noHost)
    {
        setCategory();
        setTitle(name);
        this.genre = genre;
        this.favoriteEpisode = favoriteEpisode;
    }

    public TVShow(String name, String host, String genre)
    {
        setCategory();
        setTitle(name);
        this.genre = genre;
        this.host = host;
    }

    public TVShow(String name, String genre)
    {
        setCategory();
        setTitle(name);
        this.genre = genre;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory()
    {
        this.category = "tvshow";
    }

    @Override
    public String getTitle() {
        return name;
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
        return host;
    }

    @Override
    public void setPublisher(String publisher) {
        host = publisher;
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