package com.example.techstore.controller;

import com.example.techstore.model.Product;
import com.example.techstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public String getProducts(Authentication authentication, Model model) {

        if (authentication != null) {
            String username = authentication.getName();
            System.out.println(username);
            model.addAttribute("username", username);
        } else {
            model.addAttribute("username", "Гость");
        }
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("product", new Product());
        return "productList";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/addProduct")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    // Открытие формы для редактирования продукта
    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            model.addAttribute("product", productOptional.get());
            return "editProduct"; // Отображение формы редактирования
        } else {
            return "redirect:/products";
        }
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/products";
    }

    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }
    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products;
        if (keyword == null || keyword.trim().isEmpty()) {
            products = productRepository.findAll();
        } else {
            products = productRepository.searchProducts(keyword);
        }
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "productList";
    }


    @GetMapping("/products/details/{id}")
    public String viewProductDetails(@PathVariable Long id, Model model) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            model.addAttribute("product", productOptional.get());
            return "productDetails";
        } else {
            return "redirect:/products";
        }
    }
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
