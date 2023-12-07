package com.example.Moneta;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "wishlists")
public class Wishlist {
    @Id
    private String id;
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("items")
    private List<WishlistItem> items = new ArrayList<>();

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Field("totalPrice")
    private Double totalPrice;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<WishlistItem> getItems() {
        return items;
    }

    public void setItems(List<WishlistItem> items) {
        this.items = items;
    }
    public void updateTotalPrice() {
        double sum = 0.0;
        for (WishlistItem item : items) {
            Double price = item.getItemPrice();
            if (price != null) {
                sum += price;
            }
        }
        this.totalPrice = sum;
    }

    public Double getPrice() {
        return totalPrice;
    }


}
