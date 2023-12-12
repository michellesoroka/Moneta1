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

//    public Double calculateTotalItemPrice(Wishlist wishlist) {
//        List<Wishlist.Item> items = wishlist.getItems();
//        if (items == null || items.isEmpty()) {
//            return 0.0;
//        }
//
//        // Calculate total item price
//        double total = items.stream().mapToDouble(Wishlist.Item::getitemPrice).sum();
//
//        return total;
//    }

    public Wishlist getWishlistById(String id) {
        return wishlistRepository.findById(id).orElse(null);
    }

    public void updateWishlist(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }

    public Wishlist saveOrUpdateWishlist(Wishlist wishlist) {
        wishlist.updateTotalItemPrice();
        return wishlistRepository.save(wishlist);
    }
}



//    public Double calculateTotalCostForAllWishlists() {
//        List<Wishlist> wishlists = wishlistRepository.findAll();
//        if (wishlists == null || wishlists.isEmpty()) {
//            return 0.0;
//        }
//
//        // Calculate the sum of total costs for all wishlists
//        return wishlists.stream()
//                .mapToDouble(Wishlist::getTotalItemPrice)
//                .sum();
//    }
//}

//    public Wishlist addItemToWishlist(String wishlistId, WishlistItem item) {
//        Wishlist wishlist = wishlistRepository.findById(wishlistId).orElse(null);
//        if (wishlist != null) {
//            wishlist.addItem(item);
//            return wishlistRepository.save(wishlist);
//        }
//        return null;
//    }
