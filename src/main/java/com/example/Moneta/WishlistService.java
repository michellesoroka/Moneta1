package com.example.Moneta;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Wishlist getWishlistById(String id) {
        return wishlistRepository.findById(id).orElse(null);
    }

//    public void updateWishlist(Wishlist wishlist) {
//        wishlistRepository.save(wishlist);
//    }

    public Wishlist saveOrUpdateWishlist(Wishlist wishlist) {
        wishlist.updateTotalItemPrice();
        return wishlistRepository.save(wishlist);
    }

    public void updateWishlist(Wishlist wishlist) {
        List<Wishlist.Item> updatedItems = wishlist.getItems().stream()
                .filter(item -> !item.isDeleted())
                .collect(Collectors.toList());
        wishlist.setItems(updatedItems);
        wishlist.updateTotalItemPrice();

        wishlistRepository.save(wishlist);
    }

    public double getSavedAmountFromUser(HttpServletRequest request) {
        Object savedAmountObj = request.getSession().getAttribute("savedAmount");
        if (savedAmountObj != null) {
            try {
                return Double.parseDouble(savedAmountObj.toString());
            } catch (NumberFormatException e) {
                // Handle the parsing error
                return 0.0;
            }
        }
        return 0.0; // Default value if no amount is saved
    }


    public Wishlist.Item findHighestRatedItem() {
        List<Wishlist> wishlists = wishlistRepository.findAll();

        Optional<Wishlist.Item> highestRatedItem = wishlists.stream()
                .flatMap(wishlist -> wishlist.getItems().stream())
                .max(Comparator.comparing(Wishlist.Item::getRating));

        return highestRatedItem.orElse(null);
    }

}
