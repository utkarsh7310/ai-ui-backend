package com.wowfood.backend;

import jakarta.persistence.*;

@Entity
@Table(name = "orders") // MySQL table se match kar raha hai
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private Double totalAmount;
    private String paymentMode;
    private String status = "RECEIVED";

    // Getters and Setters (Values lene aur set karne ke liye)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    // Nayi lines:
    private Integer rating = 0; // Default 0 means not rated
    private String reviewText;

    // Inke Getters aur Setters (File ke niche daal dijiye):
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
}