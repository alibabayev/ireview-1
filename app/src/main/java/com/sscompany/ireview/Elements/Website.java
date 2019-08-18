package com.sscompany.ireview.Elements;

public class Website implements Item
{
    private String category = null;
    private String name = null;
    private String use = null;
    private int thumbnail;
    private String publisher = null;
    private String parseItemId = null;

    public Website(String name, String use)
    {
        setCategory();
        this.name = name;
        this.use = use;
    }

    @Override
    public String getCategory()
    {
        return category;
    }

    @Override
    public void setCategory()
    {
        this.category = "website";
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
        return publisher;
    }

    @Override
    public void setPublisher(String publisher) {
        this.publisher = publisher;
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
