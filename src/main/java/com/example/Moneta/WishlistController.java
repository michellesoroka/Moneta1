package com.example.Moneta;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
//        Double totalItemPrice = wishlistService.calculateTotalItemPrice(newWishlist);
//        newWishlist.setTotalItemPrice(totalItemPrice);
        wishlistService.createWishlist(newWishlist);
        return "redirect:/dashboard";
    }


    @GetMapping("/wishlist/edit/{id}")
    public String showEditWishlistForm(@PathVariable("id") String id, Model model) {
        Optional<Wishlist> wishlistOpt = Optional.ofNullable(wishlistService.getWishlistById(id));

        if (!wishlistOpt.isPresent()) {
            return "redirect:/dashboard";
        }

        Wishlist wishlist = wishlistOpt.get();
        wishlist.setTotalItemPrice(wishlist.calculateTotalItemPrice());
        model.addAttribute("editWishlist", wishlist);
        return "editWishlist";
    }

    @PostMapping("/wishlist/update")
    public String updateWishlist(@ModelAttribute("editWishlist") Wishlist wishlist) {
        wishlistService.saveOrUpdateWishlist(wishlist);
        return "redirect:/dashboard";
    }
}
