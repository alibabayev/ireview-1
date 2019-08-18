package com.sscompany.ireview.Elements;

public class VideoGame implements Item
{
    private String category = null;
    private String name = null;
    private String genre = null;
    private String developer = null;
    private int year;
    private String mode = null;
    private int thumbnail;
    private String parseItemId = null;

    public VideoGame(String name, String genre, String developer, int year, String mode)
    {
        setCategory();
        setTitle(name);
        setPublisher(developer);
        this.genre = genre;
        this.year = year;
        this.mode = mode;
    }

    public VideoGame(String name, String genre, String developer, String mode)
    {
        setCategory();
        setTitle(name);
        setPublisher(developer);
        this.genre = genre;
        this.mode = mode;
    }
    public VideoGame(String name, String genre, String mode, int noDeveloper)
    {
        setCategory();
        setTitle(name);
        this.genre = genre;
        this.mode = mode;
    }
    public VideoGame(String name, String genre, String developer)
    {
        setCategory();
        setTitle(name);
        setPublisher(developer);
        this.genre = genre;
    }
    public VideoGame(String name, String genre)
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
        this.category = "videogame";
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
        return developer;
    }

    @Override
    public void setPublisher(String publisher) {
        developer = publisher;
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
