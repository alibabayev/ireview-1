package com.sscompany.ireview.Elements;

public class Movie implements Item
{
    private String category = null;
    private String name = null;
    private String director = null;
    private String leadActors = null;
    private int year;
    private String duration = null;
    private String genre = null;
    private String country = null;
    private int thumbnail;
    private String parseItemId = null;

    public Movie(String name, String director, String leadActors, int year, String duration, String genre, String country)
    {
        setCategory();
        setTitle(name);
        setPublisher(director);
        this.year = year;
        this.genre = genre;
        this.leadActors = leadActors;
        this.duration = duration;
        this.country = country;
    }

    public Movie(String name, String director, String leadActors, String genre)
    {
        setCategory();
        setTitle(name);
        setPublisher(director);
        this.genre = genre;
        this.leadActors = leadActors;
    }
    public Movie(String name, String leadActors, String genre, int noDirector)
    {
        setCategory();
        setTitle(name);
        this.genre = genre;
        this.leadActors = leadActors;
    }
    public Movie(String name, String director, String genre)
    {
        setCategory();
        setTitle(name);
        setPublisher(director);
        this.genre = genre;
    }
    public Movie(String name, String genre)
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
        this.category = "movie";
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public void setTitle(String title) {
        this.name = title;
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
        return director;
    }

    @Override
    public void setPublisher(String publisher) {
        director = publisher;
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
