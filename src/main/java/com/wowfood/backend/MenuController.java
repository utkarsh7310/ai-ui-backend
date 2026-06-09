package com.wowfood.backend;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@CrossOrigin(origins = "*") // MAGIC FIX: Ye line VS Code aur IntelliJ ki dosti karwati hai!
public class MenuController {

    private final MenuItemRepository repository;

    public MenuController(MenuItemRepository repository) {
        this.repository = repository;
    }

    // 1. Saara menu mangwane ke liye (Frontend website aur Admin panel dono ke liye)
    @GetMapping
    public List<MenuItem> getCompleteMenu() {
        return repository.findAll(); // Ye MySQL se saara menu nikal kar de dega
    }

    // 2. Naya Menu Item add karne ke liye (Admin Panel ke Menu Manager se)
    @PostMapping
    public MenuItem addMenuItem(@RequestBody MenuItem menuItem) {
        return repository.save(menuItem);
    }

    // 3. Menu Item delete karne ke liye (Admin Panel ke Menu Manager se)
    @DeleteMapping("/{id}")
    public void deleteMenuItem(@PathVariable Long id) {
        repository.deleteById(id);
    }
}