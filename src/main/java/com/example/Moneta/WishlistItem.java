package com.example.Moneta;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wishlistItems")
public class WishlistItem {
    @Id
    private String id;
    private String description;
    private String itemName;
    private String link;
    private static double itemPrice;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

//    public static double getItemPrice() {
//        return itemPrice;
//    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }


    public static double getitemPrice(Wishlist.Item item) {
        return itemPrice;
    }

}


