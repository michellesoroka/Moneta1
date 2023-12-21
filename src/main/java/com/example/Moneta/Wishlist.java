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

    private Double totalItemPrice;

    private String word;

    public void setTotalItemPrice(Double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public double getTotalItemPrice() {
        return totalItemPrice;
    }


    public static class Item {
        private String itemName;

        public void setItemPrice(double itemPrice) {
            this.itemPrice = itemPrice;
        }

        private double itemPrice;
        private String description;
        private String link;

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        private double rating;
        private boolean deleted = false;

        public boolean isDeleted() {
            return deleted;
        }
        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
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
        public double getitemPrice() {
            return itemPrice;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double calculateTotalItemPrice() {
        return items.stream().mapToDouble(Item::getItemPrice).sum();
    }

    public void updateTotalItemPrice() {
        this.totalItemPrice = calculateTotalItemPrice();
    }


}

