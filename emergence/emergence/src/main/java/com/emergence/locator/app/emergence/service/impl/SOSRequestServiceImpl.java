package com.emergence.locator.app.emergence.service.impl;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.emergence.locator.app.emergence.dto.SOSRequestDTO;
import com.emergence.locator.app.emergence.mapper.SOSRequestMapper;
import com.emergence.locator.app.emergence.model.SOSRequest;
import com.emergence.locator.app.emergence.model.User;
import com.emergence.locator.app.emergence.repository.SOSRequestRepository;
import com.emergence.locator.app.emergence.repository.UserRepository;
import com.emergence.locator.app.emergence.service.MailService;
import com.emergence.locator.app.emergence.service.SOSRequestService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SOSRequestServiceImpl implements SOSRequestService {

    private static final Logger log = LoggerFactory.getLogger(SOSRequestServiceImpl.class);

    private final SOSRequestRepository repository;

    private MailService mailService;

    private UserRepository userRepository;

    public SOSRequestServiceImpl(SOSRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<SOSRequestDTO> getAll() {
        try {
            log.info("Fetching all SOS requests...");
            return repository.findAll()
                    .stream()
                    .map(SOSRequestMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to fetch SOS requests", e);
            throw new RuntimeException("Failed to fetch SOS requests");
        }
    }

    @Override
    public SOSRequestDTO getById(Long id) {
        try {
            Optional<SOSRequest> optional = repository.findById(id);
            if (optional.isEmpty()) {
                throw new RuntimeException("SOSRequest not found with id: " + id);
            }
            return SOSRequestMapper.toDTO(optional.get());
        } catch (Exception e) {
            log.error("Failed to fetch SOSRequest by id {}", id, e);
            throw new RuntimeException("SOSRequest lookup failed");
        }
    }

    @Override
    public SOSRequestDTO save(SOSRequestDTO dto) {
        try {
            // Fetch the user entity first (e.g., from database)
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Then call mapper:
            SOSRequest entity = SOSRequestMapper.toEntity(dto, user);
            SOSRequest saved = repository.save(entity);
            mailService.sendSosNotification(
                    "admin@example.com",
                    "🚨 New SOS Alert!",
                    "An SOS request has been triggered at: \nLat: " + dto.getLatitude() +
                            "\nLng: " + dto.getLongitude() +
                            "\nStatus: " + dto.getStatus());
            log.info("Saved SOSRequest with id: {}", saved.getId());
            return SOSRequestMapper.toDTO(saved);
        } catch (Exception e) {
            log.error("Failed to save SOSRequest", e);
            throw new RuntimeException("Failed to save SOSRequest");
        }
    }

    @Override
    public SOSRequestDTO update(Long id, SOSRequestDTO dto) {
        try {
            Optional<SOSRequest> optional = repository.findById(id);
            if (optional.isEmpty()) {
                throw new RuntimeException("SOSRequest not found");
            }
            SOSRequest entity = optional.get();

            // Update status
            entity.setStatus(dto.getStatus());

            // Update location from DTO lat/lon
            Point point = new GeometryFactory().createPoint(new Coordinate(dto.getLongitude(), dto.getLatitude()));
            point.setSRID(4326);
            entity.setLocation(point);

            // Update user: fetch user entity by id (dto.getUserId())
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
            entity.setUser(user);

            // Update timestamp if DTO has it (you may want to add timestamp in DTO)
            // If not available, skip or update as needed

            SOSRequest updated = repository.save(entity);
            log.info("Updated SOSRequest with id: {}", id);
            return SOSRequestMapper.toDTO(updated);
        } catch (Exception e) {
            log.error("Failed to update SOSRequest", e);
            throw new RuntimeException("SOSRequest update failed");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                log.info("Deleted SOSRequest with id: {}", id);
            } else {
                throw new RuntimeException("SOSRequest not found");
            }
        } catch (Exception e) {
            log.error("Failed to delete SOSRequest", e);
            throw new RuntimeException("SOSRequest deletion failed");
        }
    }
}
