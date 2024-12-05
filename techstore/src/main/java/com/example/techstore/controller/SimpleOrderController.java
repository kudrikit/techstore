package com.example.techstore.controller;

import com.example.techstore.model.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@SessionAttributes("cart")
public class SimpleOrderController {

  // Получение данных корзины для оформления заказа
  @GetMapping
  public ResponseEntity<Map<String, Object>> orderPage(@ModelAttribute("cart") Cart cart) {
    Map<String, Object> response = new HashMap<>();
    response.put("items", cart.getItems());
    response.put("totalPrice", cart.getTotalPrice());
    return ResponseEntity.ok(response); // Возвращаем данные корзины для оформления заказа
  }

  // Обработка формы оформления заказа
  @PostMapping("/submit")
  public ResponseEntity<String> submitOrder(@RequestParam String name,
                                            @RequestParam String email,
                                            @RequestParam String address,
                                            @ModelAttribute("cart") Cart cart) {

    // Логика для оформления заказа (например, просто вывод в консоль)
    System.out.println("Заказ оформлен:");
    System.out.println("Имя: " + name);
    System.out.println("Email: " + email);
    System.out.println("Адрес: " + address);
    System.out.println("Общая сумма: " + cart.getTotalPrice());

    // Очистка корзины после оформления заказа
    cart.clear();

    return ResponseEntity.ok("Order submitted successfully"); // Возвращаем сообщение об успешном заказе
  }

  // Подтверждение успешного оформления заказа
  @GetMapping("/success")
  public ResponseEntity<String> orderSuccess() {
    return ResponseEntity.ok("Order completed successfully"); // Возвращаем сообщение об успешном заказе
  }
}
