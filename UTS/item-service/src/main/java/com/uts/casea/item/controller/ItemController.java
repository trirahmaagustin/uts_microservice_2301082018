package com.uts.casea.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.uts.casea.item.model.Item;
import com.uts.casea.item.service.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private static final Logger log = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    // Endpoint untuk mengambil semua item
    @GetMapping
    public List<Item> getAllItems() {
        log.info("GET /api/items accessed");
        return itemService.getAll();
    }
    
    // Endpoint untuk mengambil item berdasarkan id
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        log.info("GET /api/items/{} accessed", id);
            return itemService.getById(id)
                .map(item -> ResponseEntity.ok().body(item))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint membuat item baru
    @PostMapping
    public Item  creaItem(@RequestBody Item item) {
        log.info("POST /api/items accessed with body: {}", item);
            return itemService.createItem(item);
    }

    // Endpoint untuk update item yang sudah ada
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        log.info("PUT /api/items/{} accessed with body: {}", id, itemDetails);

        try {
            Item updateItem = itemService.updateItem(id, itemDetails);
            return ResponseEntity.ok(updateItem);
        } catch (RuntimeException e) {
            log.warn("PUT /api/items/{} failed: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint DELETE item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
    log.info("DELETE /api/items/{} accessed", id);
    Map<String, String> response = new HashMap<>();
        try {
            itemService.deleteItem(id);
            response.put("message", "Item berhasil dihapus");
            return ResponseEntity.ok("Item deleted successfully");
        } catch (RuntimeException e) {
            response.put("message", "Item tidak ditemukan dengan id" + id);
            log.warn("DELETE /api/items/{} failed: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
