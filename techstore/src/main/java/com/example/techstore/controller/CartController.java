package com.example.techstore.controller;

import com.example.techstore.model.Cart;
import com.example.techstore.model.Product;
import com.example.techstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController  // Используем @RestController для REST API
@RequestMapping("/api/cart")  // Добавляем префикс /api/cart для маршрутов корзины
@SessionAttributes("cart") // Корзина хранится в сессии
public class CartController {

  @Autowired
  private ProductRepository productRepository;

  // Создаем корзину, если её нет в сессии
  @ModelAttribute("cart")
  public Cart cart() {
    return new Cart();
  }

  // Получение текущей корзины
  @GetMapping
  public ResponseEntity<Cart> viewCart(@ModelAttribute("cart") Cart cart) {
    return ResponseEntity.ok(cart); // Возвращаем корзину в виде JSON
  }

  // Добавление товара в корзину
  @PostMapping("/add/{id}")
  public ResponseEntity<Cart> addToCart(@PathVariable Long id, @ModelAttribute("cart") Cart cart) {
    Optional<Product> productOptional = productRepository.findById(id);
    if (productOptional.isPresent()) {
      cart.addItem(productOptional.get());
      return ResponseEntity.ok(cart); // Возвращаем обновленную корзину
    } else {
      return ResponseEntity.notFound().build(); // Товар не найден
    }
  }

  // Удаление товара из корзины
  @PostMapping("/remove/{id}")
  public ResponseEntity<Cart> removeFromCart(@PathVariable Long id, @ModelAttribute("cart") Cart cart) {
    Optional<Product> productOptional = productRepository.findById(id);
    if (productOptional.isPresent()) {
      cart.removeItem(productOptional.get());
      return ResponseEntity.ok(cart); // Возвращаем обновленную корзину
    } else {
      return ResponseEntity.notFound().build(); // Товар не найден
    }
  }

  // Очистка корзины
  @PostMapping("/clear")
  public ResponseEntity<Cart> clearCart(@ModelAttribute("cart") Cart cart) {
    cart.clear(); // Очищаем корзину
    return ResponseEntity.ok(cart); // Возвращаем пустую корзину
  }
}
