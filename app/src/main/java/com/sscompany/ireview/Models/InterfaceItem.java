package com.sscompany.ireview.Models;

/**
 * This interface provides feasibility to get different items with the name of this InterfaceItem by polymorphism
 */

public interface InterfaceItem
{
    void setName(String name);
    String getName();

    void setOwner(String owner);
    String getOwner();

    void setCover_photo(String cover_photo);
    String getCover_photo();
}
