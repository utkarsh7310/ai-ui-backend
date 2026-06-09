package com.ai.uibuilder.controller;

import com.ai.uibuilder.service.AiUiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ui")
@CrossOrigin(origins = "*")
public class UiOrchestratorController {

    private final AiUiService aiUiService;

    @Value("${gemini.api.key}")
    public String apiKey;

    public UiOrchestratorController(AiUiService aiUiService) {
        this.aiUiService = aiUiService;
    }

    @GetMapping(value = "/generate", produces = "text/plain")
    public String generateUi(
            @RequestParam(defaultValue = "A responsive navbar") String prompt,
            // Naya optional parameter
            @RequestParam(required = false, defaultValue = "") String brand
    ) {
        System.out.println("Asking AI for code: " + prompt + " | Brand Clone: " + brand);
        return aiUiService.askAiForUi(prompt, brand);
    }
}