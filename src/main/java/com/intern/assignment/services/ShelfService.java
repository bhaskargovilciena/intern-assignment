package com.intern.assignment.services;

import com.intern.assignment.entities.Shelf;
import com.intern.assignment.repositories.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShelfService {
    private final ShelfRepository shelfRepository;

    @Autowired
    public ShelfService(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    public Shelf createShelf(String shelfPositionId, Shelf shelf) {
        return shelfRepository.createShelf(shelfPositionId, shelf);
    }
}
