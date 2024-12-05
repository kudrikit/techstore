package com.example.techstore.controller;

import com.example.techstore.model.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("cart")
public class SimpleOrderController {

    @GetMapping("/order")
    public String orderPage(@ModelAttribute("cart") Cart cart, Model model) {
        model.addAttribute("items", cart.getItems());
        model.addAttribute("totalPrice", cart.getTotalPrice());
        return "order";
    }

    @PostMapping("/order/submit")
    public String submitOrder(@RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String address,
                              @ModelAttribute("cart") Cart cart) {

        System.out.println("Заказ оформлен:");
        System.out.println("Имя: " + name);
        System.out.println("Email: " + email);
        System.out.println("Адрес: " + address);
        System.out.println("Общая сумма: " + cart.getTotalPrice());

        cart.clear();

        return "redirect:/order/success";
    }

    @GetMapping("/order/success")
    public String orderSuccess() {
        return "order-success";
    }
}
