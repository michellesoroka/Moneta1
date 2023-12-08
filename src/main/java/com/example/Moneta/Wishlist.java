package com.example.Moneta;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "wishlists")
public class Wishlist {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    private String id;
    private String name;
    private List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
    private Double totalPrice;

    private String word;

//    public List<WishlistItem> getItems() {
//        return items;
//    }
    public static class Item {
    private String itemName;
    private double itemPrice;
    private String description;
    private String link;
        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public double getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(double itemPrice) {
            this.itemPrice = itemPrice;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public void addItem(WishlistItem item) {
//        this.items.add(item);
//    }
//
//    public void removeItem(WishlistItem item) {
//        this.items.remove(item);
//    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

//    public void updateTotalPrice() {
//        double sum = 0.0;
//        for (WishlistItem item : items) {
//            Double price = item.getItemPrice();
//            if (price != null) {
//                sum += price;
//            }
//        }
//        this.totalPrice = sum;
//    }
}

