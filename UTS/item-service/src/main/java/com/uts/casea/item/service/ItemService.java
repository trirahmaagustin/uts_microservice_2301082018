package com.uts.casea.item.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uts.casea.item.model.Item;
import com.uts.casea.item.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> getById(Long id) {
        return itemRepository.findById(id);
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item itemDetails) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not foundd with id " + id));
        item.setName(itemDetails.getName());
        item.setPrice(itemDetails.getPrice());
        item.setStock(itemDetails.getStock());
        item.setDescription(itemDetails.getDescription());
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Item not found with id" + id));
        itemRepository.delete(item);
    }
    
}
