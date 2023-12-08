package com.example.Moneta;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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

    public void createWishlist(Wishlist newWishlist) {
        wishlistRepository.save(newWishlist);
    }

//    public Wishlist addItemToWishlist(String wishlistId, WishlistItem item) {
//        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElse(null);
//        if (wishlist != null) {
//            wishlist.addItem(item);
//            return wishlistRepository.save(wishlist);
//        }
//        return null;
//    }
}