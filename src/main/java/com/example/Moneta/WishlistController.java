package com.example.Moneta;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showDashboard(Model model, HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        if (username == null || username.isEmpty()) {
            return "redirect:/login";
        }

        List<Wishlist> wishlists = wishlistService.getAllWishlists();
        Wishlist.Item highestRatedItem = wishlistService.findHighestRatedItem();


        for (Wishlist wishlist : wishlists) {
            wishlist.updateTotalItemPrice();
        }

        double savedAmount = wishlistService.getSavedAmountFromUser(request);
        double totalItemPrice = wishlists.stream().mapToDouble(Wishlist::getTotalItemPrice).sum();
        double difference = savedAmount - totalItemPrice;

        model.addAttribute("username", username);
        model.addAttribute("message", "Click the button below to get started!");
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("newWishlist", new Wishlist());
        model.addAttribute("newItem", new WishlistItem());
        model.addAttribute("difference", difference);
        model.addAttribute("savedAmount", savedAmount);
        model.addAttribute("highestRatedItem", highestRatedItem);
        model.addAttribute("totalItemPrice", totalItemPrice);

        return "dashboard";
    }


    @PostMapping("/save-saved-amount")
    public String saveSavedAmount(HttpServletRequest request, @RequestParam("savedAmount") double savedAmount) {
        request.getSession().setAttribute("savedAmount", savedAmount);
        return "redirect:/dashboard";
    }

    private double calculateTotalItemPrice(List<Wishlist> wishlists) {
        return wishlists.stream()
                .mapToDouble(Wishlist::getTotalItemPrice)
                .sum();
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        // Simple check with hardcoded credentials
        if ("michellesoroka".equals(username) && "moneta".equals(password)) {
            request.getSession().setAttribute("username", "Michelle");
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("loginError", "Invalid username or password");
            return "redirect:/login";
        }
    }

    @GetMapping("/wishlist/create")
    public String showCreateWishlistForm(Model model) {
        Wishlist newWishlist = new Wishlist();
        newWishlist.setItems(new ArrayList<>());
        model.addAttribute("newWishlist", newWishlist);
        System.out.println("Reached create wishlist endpoint! Pls continue to work!");
        return "createWishlist";
    }

    @PostMapping("/wishlist/save")
    public String saveWishlist(@ModelAttribute("newWishlist") Wishlist newWishlist) {
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


        if (!model.containsAttribute("editWishlist")) {
            model.addAttribute("editWishlist", wishlist);
        }

        return "editWishlist";
    }

    @PostMapping("/wishlist/update")
    public String updateWishlist(@ModelAttribute("editWishlist") Wishlist wishlist) {
        wishlistService.updateWishlist(wishlist);
        return "redirect:/dashboard";
    }

    @GetMapping("/wishlist/addItem/{id}")
    public String addItemToWishlist(@PathVariable("id") String id, Model model) {
        Wishlist wishlist = wishlistService.getWishlistById(id);
        if (wishlist == null) {
            return "redirect:/dashboard";
        }

        wishlist.getItems().add(new Wishlist.Item());

        // Ensure that editWishlist is not null in the model
        if (!model.containsAttribute("editWishlist")) {
            model.addAttribute("editWishlist", wishlist);
        }

        return "editWishlist";
    }

    @DeleteMapping("/wishlist/delete/{id}")
    public ResponseEntity<String> deleteWishlist(@PathVariable String id) {
        try {
            wishlistRepository.deleteById(id);
            return ResponseEntity.ok("Wishlist deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete wishlist.");
        }
    }
}
