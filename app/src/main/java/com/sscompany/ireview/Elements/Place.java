package com.sscompany.ireview.Elements;

public class Place implements Item
{
    private String category = null;
    private String name = null;
    private String publisher = null;
    private String address = null;
    private String workingHours = null;
    private String type = null;
    private int thumbnail;
    private String parseItemId = null;

    public Place(String name, String address, String workingHours, String type)
    {
        setCategory();
        setTitle(name);
        this.address = address;
        this.workingHours = workingHours;
        this.type = type;
    }

    public Place(String name, String address, String type)
    {
        setCategory();
        setTitle(name);
        this.address = address;
        this.type = type;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory()
    {
        this.category = "place";
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
