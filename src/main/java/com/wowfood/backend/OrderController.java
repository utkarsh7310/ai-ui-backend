package com.wowfood.backend;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderRepository repository;

    public OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    // 1. Naya order receive karna (Aapki User Website ke liye)
    @PostMapping
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody Order order) {
        // Order save karo
        Order savedOrder = repository.save(order);

        // MAGIC FIX: Infinite JSON error rokne ke liye ekdum clean response bhejo
        Map<String, Object> response = new HashMap<>();
        response.put("id", savedOrder.getId());
        response.put("userName", savedOrder.getUserName());
        response.put("paymentMode", savedOrder.getPaymentMode());
        response.put("status", savedOrder.getStatus());
        response.put("totalAmount", savedOrder.getTotalAmount());

        return ResponseEntity.ok(response);
    }

    // 2. Saare orders bhejna (Aapke Admin Panel ke liye)
    @GetMapping
    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    // 3. Order ka status update karna (Jaise 'Cooking' ya 'Delivered')
    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Order order = repository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        Order savedOrder = repository.save(order);

        // Safe Response
        Map<String, Object> response = new HashMap<>();
        response.put("id", savedOrder.getId());
        response.put("status", savedOrder.getStatus());
        return ResponseEntity.ok(response);
    }

    // 4. User ki order history bhejna
    @GetMapping("/user/{userName}")
    public List<Order> getUserOrders(@PathVariable String userName) {
        return repository.findByUserNameOrderByIdDesc(userName);
    }

    // 5. Order ka Review aur Rating save karna
    @PutMapping("/{id}/review")
    public ResponseEntity<Map<String, Object>> addReview(@PathVariable Long id, @RequestParam Integer rating, @RequestParam(required = false) String reviewText) {
        Order order = repository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setRating(rating);
        order.setReviewText(reviewText);
        repository.save(order);

        // Safe Response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Review Saved Successfully!");
        return ResponseEntity.ok(response);
    }
}