package com.example.Moneta;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }


    public List<Wishlist> getAllWishlists() {
        return wishlistRepository.findAll();
    }

    public Wishlist createWishlist(Wishlist wishlist) {
        return wishlistRepository.save(wishlist);
    }

//    public Wishlist getWishlistById(String id) {
//        return wishlistRepository.findById(id).orElse(null);
//    }

    public Wishlist getWishlistById(String wishlistId) {
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishlistId);
        return optionalWishlist.orElse(null);
    }

    public void addItemToWishlist(String wishlistId, WishlistItem item) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElse(null);
        if (wishlist != null) {
            wishlist.getItems().add(item);
            wishlistRepository.save(wishlist);
        }
    }

    public void deleteItemFromWishlist(String wishlistId, String itemId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElse(null);
        if (wishlist != null) {
            wishlist.getItems().removeIf(item -> item.getId().equals(itemId));
            wishlistRepository.save(wishlist);
        }
    }

    public void updateWishlist(Wishlist updatedWishlist) {
        Wishlist existingWishlist = wishlistRepository.findById(updatedWishlist.getId()).orElse(null);

        if (existingWishlist != null) {
            existingWishlist.setName(updatedWishlist.getName());

            if (updatedWishlist.getDescription() != null && !updatedWishlist.getDescription().isEmpty()) {
                existingWishlist.setDescription(updatedWishlist.getDescription());
            }
            List<WishlistItem> updatedItems = updatedWishlist.getItems();
            List<WishlistItem> existingItems = existingWishlist.getItems();
            for (WishlistItem updatedItem : updatedItems) {
                for (WishlistItem existingItem : existingItems) {
                    if (updatedItem.getId().equals(existingItem.getId())) {
                        existingItem.setItemPrice(updatedItem.getItemPrice());
                        break;
                    }
                }
            }

            wishlistRepository.save(existingWishlist);
        }
    }


//    public void updateWishlist(Wishlist updatedWishlist) {
//        Wishlist existingWishlist = wishlistRepository.findById(updatedWishlist.getId()).orElse(null);
//
//        if (existingWishlist != null) {
//            existingWishlist.setName(updatedWishlist.getName());
//            existingWishlist.setDescription(updatedWishlist.getDescription());
//            existingWishlist.set(updatedWishlist.getPrice());
//            wishlistRepository.save(existingWishlist);
//        }
//    }
}

