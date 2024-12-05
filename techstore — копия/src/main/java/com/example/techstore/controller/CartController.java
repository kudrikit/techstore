package com.example.techstore.controller;

import com.example.techstore.model.Cart;
import com.example.techstore.model.Product;
import com.example.techstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@SessionAttributes("cart") // Корзина хранится в сессии
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }

    @GetMapping("/cart")
    public String viewCart(@ModelAttribute("cart") Cart cart, Model model) {
        model.addAttribute("items", cart.getItems());
        model.addAttribute("totalPrice", cart.getTotalPrice());
        return "cart";
    }

    @PostMapping("/cart/add/{id}")
    public String addToCart(@PathVariable Long id, @ModelAttribute("cart") Cart cart) {
        Optional<Product> productOptional = productRepository.findById(id);
        productOptional.ifPresent(cart::addItem);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id, @ModelAttribute("cart") Cart cart) {
        Optional<Product> productOptional = productRepository.findById(id);
        productOptional.ifPresent(cart::removeItem);
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(@ModelAttribute("cart") Cart cart) {
        cart.clear();
        return "redirect:/cart";
    }
}
