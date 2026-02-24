package com.intern.assignment.services;

import com.intern.assignment.entities.ShelfPosition;
import com.intern.assignment.repositories.ShelfPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShelfPositionService {
    private final ShelfPositionRepository shelfPositionRepository;

    @Autowired
    public ShelfPositionService(ShelfPositionRepository shelfPositionRepository) {
        this.shelfPositionRepository = shelfPositionRepository;
    }

    public List<ShelfPosition> createShelfPositions(String deviceId, int numberOfShelfPositions) {
        return shelfPositionRepository.createShelfPosition(deviceId, numberOfShelfPositions);
    }

    public List<Map<String,Object>> getShelfPositions(String deviceId) {
        return shelfPositionRepository.getShelfPositions(deviceId);
    }
}
