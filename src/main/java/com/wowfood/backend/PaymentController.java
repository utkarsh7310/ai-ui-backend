package com.wowfood.backend;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "*")
public class PaymentController {

    // ✅ Razorpay Keys Integrated
    private static final String KEY_ID = "rzp_test_STtZsNYQJIvgGG";
    private static final String KEY_SECRET = "YOUR_SECRET_KEY";

    @PostMapping("/createOrder")
    public String createOrder(@RequestBody Map<String, Object> data) throws Exception {
        System.out.println("Payment request aayi hai!");

        // Frontend se amount (rupaye mein) fetch karna
        int amount = (int) data.get("amount");

        // Razorpay ko connect karo
        RazorpayClient razorpay = new RazorpayClient(KEY_ID, KEY_SECRET);

        // Razorpay ka order object banao
        JSONObject orderRequest = new JSONObject();
        // Amount ko paise mein convert karna (1 INR = 100 Paise)
        orderRequest.put("amount", amount * 100);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

        // Order create karna
        Order order = razorpay.orders.create(orderRequest);

        // Frontend ko response bhejna (Order ID isi mein hogi)
        return order.toString();
    }
}