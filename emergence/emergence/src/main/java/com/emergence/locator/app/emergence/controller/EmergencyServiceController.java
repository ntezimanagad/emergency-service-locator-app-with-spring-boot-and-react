package com.emergence.locator.app.emergence.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.emergence.locator.app.emergence.dto.EmergencyServiceDTO;
import com.emergence.locator.app.emergence.service.EmergencyServiceService;

import java.util.List;

@RestController
@RequestMapping("/api/emergency-services")
public class EmergencyServiceController {

    private static final Logger log = LoggerFactory.getLogger(EmergencyServiceController.class);

    private final EmergencyServiceService emergencyServiceService;

    public EmergencyServiceController(EmergencyServiceService emergencyServiceService) {
        this.emergencyServiceService = emergencyServiceService;
    }

    // ✅ Get all services
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<EmergencyServiceDTO> services = emergencyServiceService.getAll();
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            log.error("Failed to fetch emergency services", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching emergency services");
        }
    }

    // // ✅ Get service by ID
    // @GetMapping("/{id}")
    // public ResponseEntity<?> getById(@PathVariable Long id) {
    //     try {
    //         EmergencyServiceDTO dto = emergencyServiceService.getById(id);
    //         return ResponseEntity.ok(dto);
    //     } catch (Exception e) {
    //         log.error("Failed to fetch emergency service with id {}", id, e);
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //                 .body("Emergency service not found");
    //     }
    // }

    // ✅ Create new service
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmergencyServiceDTO dto) {
        try {
            EmergencyServiceDTO saved = emergencyServiceService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            log.error("Failed to create emergency service", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create emergency service");
        }
    }

    // ✅ Update existing service
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody EmergencyServiceDTO dto) {
        try {
            EmergencyServiceDTO updated = emergencyServiceService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            log.error("Failed to update emergency service with id {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update emergency service");
        }
    }

    // ✅ Delete service
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            emergencyServiceService.delete(id);
            return ResponseEntity.ok("Emergency service deleted successfully");
        } catch (Exception e) {
            log.error("Failed to delete emergency service with id {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to delete emergency service");
        }
    }
}
