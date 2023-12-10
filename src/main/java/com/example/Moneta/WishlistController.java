package com.example.Moneta;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;
    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistController(WishlistService wishlistService, WishlistRepository wishlistRepository) {
        this.wishlistService = wishlistService;
        this.wishlistRepository = wishlistRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Wishlist> wishlists = wishlistService.getAllWishlists();
        model.addAttribute("message", "Welcome to Moneta!");
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("newWishlist", new Wishlist());
        model.addAttribute("newItem", new WishlistItem());
        return "dashboard";
    }


    @GetMapping("/wishlist/create")
    public String showCreateWishlistForm(Model model) {
        Wishlist newWishlist = new Wishlist();
        newWishlist.setItems(new ArrayList<>());
        model.addAttribute("newWishlist", newWishlist);
        return "createWishlist";
    }

    @PostMapping("/wishlist/save")
    public String saveWishlist(@ModelAttribute("newWishlist") Wishlist newWishlist) {
        Double totalItemPrice = wishlistService.calculateTotalItemPrice(newWishlist);
        newWishlist.setTotalItemPrice(totalItemPrice);
        wishlistService.createWishlist(newWishlist);
        return "redirect:/dashboard";
    }
}

//    @PostMapping("/{wishlistId}/addItem")
//    public String addItemToWishlist(@PathVariable String wishlistId, @ModelAttribute("newItem") WishlistItem item) {
//        wishlistService.addItemToWishlist(wishlistId, item);
//        return "redirect:/dashboard"; // Redirect to a dashboard or any other page
//    }
//}`