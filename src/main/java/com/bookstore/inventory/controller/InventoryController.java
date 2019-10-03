package com.bookstore.inventory.controller;

import com.bookstore.inventory.model.Inventory;
import com.bookstore.inventory.model.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class InventoryController {

    @Autowired
    private InventoryRepository repository;

    // Find
    @GetMapping("/inventory")
    List<Inventory> findAll() {
        return repository.findAll();
    }

    // Find
    @GetMapping("/inventory/{id}")
    Inventory findOne(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
    }

    // Save
    @PostMapping("/inventory")
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    Inventory newInventory(@RequestBody Inventory newInventory) {
        return repository.save(newInventory);
    }

    // Save
    @PutMapping("/inventory/{id}")
    Inventory saveOrUpdate(@RequestBody Inventory newinventory, @PathVariable Long id) {
        return repository.findById(id)
                .map(x -> {
                    x.setQuantity(newinventory.getQuantity());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newinventory.setId(id);
                    return repository.save(newinventory);
                });
    }

    //Increment inventory
    @PutMapping("/inventory/increment")
    Inventory incrementInventory(@RequestParam Long id, @RequestParam int quantity) {
        Inventory inventory = repository.findById(id)
                .orElseThrow( () -> new NoSuchElementException());
        inventory.setQuantity(inventory.getQuantity()+quantity);
        return repository.save(inventory);
    }


    //Increment inventory
    @PutMapping("/inventory/decrement")
    Inventory decrementInventory(@RequestParam Long id, @RequestParam int quantity) {
        Inventory inventory = repository.findById(id)
                .orElseThrow( () -> new NoSuchElementException());
        inventory.setQuantity(inventory.getQuantity()-quantity);
        return repository.save(inventory);
    }


}
