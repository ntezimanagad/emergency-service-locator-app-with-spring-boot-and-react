package com.emergence.locator.app.emergence.service.impl;

import com.emergence.locator.app.emergence.dto.EmergencyServiceDTO;
import com.emergence.locator.app.emergence.mapper.EmergencyServiceMapper;
import com.emergence.locator.app.emergence.model.EmergencyService;
import com.emergence.locator.app.emergence.model.ServiceType;
import com.emergence.locator.app.emergence.repository.EmergencyServiceRepository;
import com.emergence.locator.app.emergence.service.EmergencyServiceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmergencyServiceServiceImpl implements EmergencyServiceService {

    private static final Logger log = LoggerFactory.getLogger(EmergencyServiceServiceImpl.class);
    private final EmergencyServiceRepository repository;

    public EmergencyServiceServiceImpl(EmergencyServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EmergencyServiceDTO> getAll() {
        try {
            log.info("Fetching all emergency services...");
            return repository.findAll()
                    .stream()
                    .map(EmergencyServiceMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching emergency services", e);
            throw new RuntimeException("Failed to fetch emergency services");
        }
    }

    @Override
    public EmergencyServiceDTO save(EmergencyServiceDTO dto) {
        try {
            EmergencyService service = EmergencyServiceMapper.toEntity(dto);
            EmergencyService saved = repository.save(service);
            log.info("Saved emergency service: {}", saved.getName());
            return EmergencyServiceMapper.toDTO(saved);
        } catch (Exception e) {
            log.error("Failed to save emergency service", e);
            throw new RuntimeException("Service creation failed");
        }
    }

    @Override
    public EmergencyServiceDTO update(Long id, EmergencyServiceDTO dto) {
        try {
            Optional<EmergencyService> optional = repository.findById(id);
            if (optional.isEmpty()) {
                throw new RuntimeException("Service not found");
            }

            EmergencyService service = optional.get();
            service.setName(dto.getName());
            service.setPhone(dto.getPhone());
            service.setAddress(dto.getAddress());
            service.setType(ServiceType.valueOf(dto.getType()));

            // Set latitude and longitude directly (no Point)
            service.setLatitude(dto.getLatitude());
            service.setLongitude(dto.getLongitude());

            EmergencyService updated = repository.save(service);
            log.info("Updated emergency service with id: {}", id);
            return EmergencyServiceMapper.toDTO(updated);
        } catch (Exception e) {
            log.error("Failed to update emergency service", e);
            throw new RuntimeException("Service update failed");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                log.info("Deleted emergency service with id: {}", id);
            } else {
                throw new RuntimeException("Service not found");
            }
        } catch (Exception e) {
            log.error("Failed to delete service", e);
            throw new RuntimeException("Service deletion failed");
        }
    }

    @Override
    public List<EmergencyServiceDTO> findNearby(double lat, double lng, double radiusInKm) {
        try {
            // You need to implement this repository method using bounding box logic
            double radiusInMeters = radiusInKm * 1000;
            List<EmergencyService> nearby = repository.findNearby(lat, lng, radiusInMeters);
            return nearby.stream()
                    .map(EmergencyServiceMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to find nearby services", e);
            throw new RuntimeException("Nearby search failed");
        }
    }
}
