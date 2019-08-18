package com.sscompany.ireview.Elements;

public class Book implements Item
{
    private String category = null;
    private String name = null;
    private String author = null;
    private int numberOfPages;
    private String genre = null;
    private String releaseDate = null;
    private String language = null;
    private int thumbnail;
    private String parseItemId;


    public Book(String name, String author, int numberOfPages, String genre, String releaseDate, String language, int thumbnail)
    {
        setCategory();
        setTitle(name);
        setThumbnail(thumbnail);
        setPublisher(author);
        this.numberOfPages = numberOfPages;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.language = language;
    }

    public Book(String name, String author, int numberOfPages, String genre, String releaseDate, String language)
    {
        setCategory();
        setTitle(name);
        setPublisher(author);
        this.numberOfPages = numberOfPages;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.language = language;
    }

    public Book(String name, String author, String genre)
    {
        setCategory();
        setTitle(name);
        setPublisher(author);
        this.genre = genre;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory()
    {
        this.category = "book";
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public void setTitle(String title)
    {
        this.name = title;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String getPublisher() {
        return author;
    }

    @Override
    public void setPublisher(String publisher) {
        this.author = publisher;
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
