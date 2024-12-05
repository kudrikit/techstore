package com.example.techstore.controller;

import com.example.techstore.model.Product;
import com.example.techstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")  // Базовый URL для всех маршрутов продуктов
public class ProductController {

  @Autowired
  private ProductRepository productRepository;

  // Получение списка продуктов
  @GetMapping
  public ResponseEntity<List<Product>> getProducts() {
    List<Product> products = productRepository.findAll();
    return ResponseEntity.ok(products); // Возвращаем JSON с продуктами
  }

  // Получение деталей продукта по ID
  @GetMapping("/details/{id}")
  public ResponseEntity<Product> viewProductDetails(@PathVariable Long id) {
    Optional<Product> productOptional = productRepository.findById(id);
    return productOptional.map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Возвращаем 404, если продукт не найден
  }

  // Добавление нового продукта (доступно только для администратора)
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public ResponseEntity<Product> addProduct(@RequestBody Product product) {
    Product savedProduct = productRepository.save(product); // Сохранение в БД
    return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct); // Возвращаем сохраненный продукт
  }

  // Обновление продукта (доступно только для администратора)
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
    return productRepository.findById(id)
      .map(product -> {
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setImageUrl(updatedProduct.getImageUrl());
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.ok(savedProduct); // Возвращаем обновленный продукт
      })
      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // Возвращаем 404, если продукт не найден
  }

  // Удаление продукта (доступно только для администратора)
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    if (productRepository.existsById(id)) {
      productRepository.deleteById(id); // Удаление продукта из БД
      return ResponseEntity.noContent().build(); // Возвращаем статус 204 No Content
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Возвращаем 404, если продукт не найден
    }
  }

  // Поиск продуктов по ключевому слову
  @GetMapping("/search")
  public ResponseEntity<List<Product>> searchProducts(@RequestParam("keyword") String keyword) {
    List<Product> products;
    if (keyword == null || keyword.trim().isEmpty()) {
      products = productRepository.findAll(); // Показываем все товары
    } else {
      products = productRepository.searchProducts(keyword); // Ищем по ключевому слову
    }
    return ResponseEntity.ok(products); // Возвращаем найденные продукты
  }

  // Страница "О нас" - просто заглушка для REST API
  @GetMapping("/about")
  public ResponseEntity<String> about() {
    return ResponseEntity.ok("This is the about page content"); // Можно заменить на реальные данные, если нужно
  }
}
