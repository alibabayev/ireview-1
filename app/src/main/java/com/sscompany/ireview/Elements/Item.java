package com.sscompany.ireview.Elements;

public class Item
{
    private String category;
    private String item_id;

    public Item(String category, String item_id) {
        this.category = category;
        this.item_id = item_id;
    }

    public Item() {

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "category='" + category + '\'' +
                ", item_id='" + item_id + '\'' +
                '}';
    }
}
