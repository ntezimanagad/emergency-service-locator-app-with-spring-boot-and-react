package com.emergence.locator.app.emergence.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.emergence.locator.app.emergence.dto.SOSRequestDTO;
import com.emergence.locator.app.emergence.model.User;
import com.emergence.locator.app.emergence.repository.UserRepository;
import com.emergence.locator.app.emergence.service.SOSRequestService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sos")
public class SOSRequestController {

    private static final Logger log = LoggerFactory.getLogger(SOSRequestController.class);

    private final SOSRequestService sosRequestService;
    private final UserRepository userRepository;

    public SOSRequestController(SOSRequestService sosRequestService, UserRepository userRepository) {
        this.sosRequestService = sosRequestService;
        this.userRepository = userRepository;
    }

    // ✅ Get all SOS requests
    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        try {
            List<SOSRequestDTO> requests = sosRequestService.getAll();
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            log.error("Failed to fetch SOS requests", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching SOS requests");
        }
    }

    // ✅ Get SOS request by ID
    @GetMapping("getById/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            SOSRequestDTO dto = sosRequestService.getById(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error("Failed to fetch SOS request with id {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("SOS request not found");
        }
    }

    // ✅ Create new SOS request
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SOSRequestDTO dto) {
        try {
            SOSRequestDTO saved = sosRequestService.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            log.error("Failed to create SOS request", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create SOS request");
        }
    }

    // ✅ Update existing SOS request
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody SOSRequestDTO dto) {
        try {
            SOSRequestDTO updated = sosRequestService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            log.error("Failed to update SOS request with id {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to update SOS request");
        }
    }

    // ✅ Delete SOS request
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            sosRequestService.delete(id);
            return ResponseEntity.ok("SOS request deleted successfully");
        } catch (Exception e) {
            log.error("Failed to delete SOS request with id {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to delete SOS request");
        }
    }
    @GetMapping("/me")
    public ResponseEntity<?> getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User Not Found");
        }

        User user = userOpt.get();

        return ResponseEntity.ok(Map.of("id", user.getId()));
    }
}
