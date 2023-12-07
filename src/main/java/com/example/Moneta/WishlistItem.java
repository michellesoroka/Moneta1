package com.example.Moneta;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "WishlistItems")
public class WishlistItem {
    @Id
    private String id;
    private String description;
    private String itemName;

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    private double itemPrice;


    public double getPrice() {
        return itemPrice;
    }

    public void setPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public double getItemPrice() {
        return itemPrice;
    }
}
