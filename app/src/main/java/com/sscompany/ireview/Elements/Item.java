package com.sscompany.ireview.Elements;

public interface Item
{
    String getCategory();
    void setCategory();

    String getTitle();
    void setTitle(String title);

    int getThumbnail();
    void setThumbnail(int thumbnail);

    String getPublisher();
    void setPublisher(String publisher);

    String getId();
    void setId(String id);
}
