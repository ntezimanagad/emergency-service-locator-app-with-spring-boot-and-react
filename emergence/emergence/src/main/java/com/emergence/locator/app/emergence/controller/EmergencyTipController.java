package com.emergence.locator.app.emergence.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.emergence.locator.app.emergence.dto.EmergencyTipDTO;
import com.emergence.locator.app.emergence.service.EmergencyTipService;

import java.util.List;

@RestController
@RequestMapping("/api/tips")
public class EmergencyTipController {

    private static final Logger log = LoggerFactory.getLogger(EmergencyTipController.class);

    private final EmergencyTipService emergencyTipService;

    public EmergencyTipController(EmergencyTipService emergencyTipService) {
        this.emergencyTipService = emergencyTipService;
    }

    // ✅ Get all tips
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<EmergencyTipDTO> tips = emergencyTipService.getAll();
            return ResponseEntity.ok(tips);
        } catch (Exception e) {
            log.error("Failed to get tips", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch tips");
        }
    }

    // ✅ Get one by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            EmergencyTipDTO dto = emergencyTipService.getById(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("Failed to get tip by id {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Emergency tip not found");
        }
    }

    // ✅ Create a tip
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmergencyTipDTO dto) {
        try {
            EmergencyTipDTO saved = emergencyTipService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            log.error("Failed to create emergency tip", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create emergency tip");
        }
    }

    // ✅ Update a tip
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EmergencyTipDTO dto) {
        try {
            EmergencyTipDTO updated = emergencyTipService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            log.error("Failed to update emergency tip with id {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update emergency tip");
        }
    }

    // ✅ Delete a tip
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            emergencyTipService.delete(id);
            return ResponseEntity.ok("Emergency tip deleted successfully");
        } catch (Exception e) {
            log.error("Failed to delete emergency tip with id {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to delete emergency tip");
        }
    }
}