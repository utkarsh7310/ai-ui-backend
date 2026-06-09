package com.ai.uibuilder.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiUiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    // Ab hum brandUrl bhi pass karenge
    public String askAiForUi(String userRequirement, String brandUrl) {

        String brandInstruction = "";
        if (brandUrl != null && !brandUrl.trim().isEmpty()) {
            brandInstruction = "CRITICAL BRAND CLONING RULE: The user wants this UI to look EXACTLY like the brand at '" + brandUrl + "'. " +
                    "Analyze or infer this brand's visual identity (primary colors, font weights, border radius, hover effects, shadow depth, dark/light mode preference). " +
                    "Clone their aesthetic perfectly using Tailwind classes. For example, if it's Apple, use sleek minimalist grayscale with heavy backdrop-blur. If it's Stripe, use vibrant gradients and sharp drop-shadows. ";
        } else {
            brandInstruction = "Make it highly futuristic, dark-themed, with glassmorphism and glowing accents. ";
        }

        String prompt = "Act as an Expert Frontend Web Developer and UI Designer. " + brandInstruction +
                "Build a fully responsive UI for: " + userRequirement + ". " +
                "RULES: " +
                "1. Use pure HTML5 and Tailwind CSS only. " +
                "2. Make it 100% Responsive using sm:, md:, lg: prefixes. " +
                "3. OUTPUT FORMAT: Return ONLY valid, ready-to-render HTML code starting with <div... and ending with </div>. " +
                "Do NOT include ```html markdown tags. Do NOT include <html>, <head>, or <body> tags.";

        String baseUrl = "https://" + "generativelanguage.googleapis.com/v1/models/gemini-3.5-flash:generateContent?key=";
        String url = baseUrl + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"contents\": [{\"parts\": [{\"text\": \"%s\"}]}]}", prompt.replace("\"", "\\\""));
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            String aiResult = root.at("/candidates/0/content/parts/0/text").asText();
            return aiResult.replace("```html", "").replace("```", "").trim();
        } catch (Exception e) {
            System.err.println("API Error: " + e.getMessage());
            return "<div class='text-red-500 p-4'>Error generating UI code. Please try again.</div>";
        }
    }
}