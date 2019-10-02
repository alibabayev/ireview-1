package com.sscompany.ireview.Models;

/**
 * This interface provides feasibility to get different items with the name of this InterfaceItem by polymorphism
 */

public interface InterfaceItem
{
    void setName(String name);
    String getName();

    void setType(String type);
    String getType();

    void setOwner(String owner);
    String getOwner();

    void setDetail(String detail);
    String getDetail();

    void setCover_photo(String cover_photo);
    String getCover_photo();

    void setCategory(String category);
    String getCategory();
}
