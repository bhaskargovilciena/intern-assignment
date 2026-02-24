package com.intern.assignment.controllers;

import com.intern.assignment.entities.Shelf;
import com.intern.assignment.services.ShelfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shelf")
public class ShelfController {
    private final ShelfService shelfService;
    private final Logger logger = LoggerFactory.getLogger(ShelfController.class);

    @Autowired
    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @PostMapping("/create")
    public ResponseEntity<Shelf> createShelf(@RequestParam(value = "shelfPositionId") String shelfPositionId, @RequestBody Shelf shelf) {
        logger.info("Shelf Controller: Shelf creation requested");
        return new ResponseEntity<>(shelfService.createShelf(shelfPositionId, shelf), HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Shelf> getShelf(@RequestParam (value = "shelfPositionId") String shelfPositionId) {
        logger.info("Shelf Controller: Shelf read requested");
        return new ResponseEntity<>(shelfService.getShelf(shelfPositionId), HttpStatus.OK);
    }
}
