package com.example.Moneta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;
    double totalPrice = 0;

    @Autowired
    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Wishlist> wishlists = wishlistService.getAllWishlists();
        model.addAttribute("message", "Welcome to Moneta!");
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("newWishlist", new Wishlist());
        model.addAttribute("newItem", new WishlistItem());
        model.addAttribute("totalPrice", totalPrice);
        totalPrice = wishlists.stream()
                .flatMap(wishlist -> wishlist.getItems().stream())
                .mapToDouble(WishlistItem::getPrice)
                .sum();

        return "dashboard";
    }

    @GetMapping("/wishlist/create")
    public String showCreateWishlistForm(Model model) {
        model.addAttribute("newWishlist", new Wishlist());
        return "createWishlist";
    }


    @PostMapping("/wishlist/save")
    public String saveNewWishlist(@ModelAttribute("newWishlist") Wishlist wishlist) {
        System.out.println("Received Wishlist: " + wishlist.toString());
        wishlistService.createWishlist(wishlist);
        return "redirect:/dashboard";
    }

    @PostMapping("/wishlist/deleteItem/{wishlistId}/{itemId}")
    public String deleteItemFromWishlist(@PathVariable String wishlistId, @PathVariable String itemId) {
        wishlistService.deleteItemFromWishlist(wishlistId, itemId);
        return "redirect:/dashboard";
    }

    @PostMapping("/wishlist/edit")
    public String updateWishlist(@ModelAttribute("wishlist") Wishlist updatedWishlist,
                                 @RequestParam("itemIndex") int itemIndex,
                                 @RequestParam("newDescription") String newDescription,
                                 @RequestParam("newItemPrice") String newItemPrice) {

        Double price = Double.parseDouble(newItemPrice);

        if (itemIndex >= 0 && itemIndex < updatedWishlist.getItems().size()) {
            WishlistItem itemToUpdate = updatedWishlist.getItems().get(itemIndex);
            itemToUpdate.setItemPrice(price);
        }

        if (newDescription != null && !newDescription.isEmpty()) {
            updatedWishlist.setDescription(newDescription);
        }

        wishlistService.updateWishlist(updatedWishlist);
        return "redirect:/dashboard";
    }

    @GetMapping("/wishlist/edit/{wishlistId}")
    public String editWishlist(@PathVariable String wishlistId, Model model) {
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId);
        if (wishlist != null) {
            model.addAttribute("wishlist", wishlist);
            return "editWishlist"; // Thymeleaf template for editing wishlist
        } else {
            return "error"; // Handle error if wishlist not found
        }
    }

    //    @PostMapping("/wishlist/update")
//    public String updateWishlist(@ModelAttribute("wishlist") Wishlist updatedWishlist) {
//        wishlistService.updateWishlist(updatedWishlist);
//        return "redirect:/dashboard";
//    }
    @GetMapping("/wishlist/{wishlistId}")
    public String viewWishlist(@PathVariable String wishlistId, Model model) {
        Wishlist wishlist = wishlistService.getWishlistById(wishlistId); // Implement this method in WishlistService
        if (wishlist != null) {
            model.addAttribute("wishlist", wishlist);
            return "viewWishlist";
        } else {
            return "error";
        }
    }
}

