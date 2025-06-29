package com.emergence.locator.app.emergence.service.impl;

import com.emergence.locator.app.emergence.dto.SOSRequestDTO;
import com.emergence.locator.app.emergence.mapper.SOSRequestMapper;
import com.emergence.locator.app.emergence.model.SOSRequest;
import com.emergence.locator.app.emergence.model.User;
import com.emergence.locator.app.emergence.repository.SOSRequestRepository;
import com.emergence.locator.app.emergence.repository.UserRepository;
import com.emergence.locator.app.emergence.service.MailService;
import com.emergence.locator.app.emergence.service.SOSRequestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SOSRequestServiceImpl implements SOSRequestService {

    private static final Logger log = LoggerFactory.getLogger(SOSRequestServiceImpl.class);

    private final SOSRequestRepository repository;

    @Autowired
    private MailService mailService;

    @Autowired
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
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Map DTO to entity without geometry
            SOSRequest entity = SOSRequestMapper.toEntity(dto, user);
            SOSRequest saved = repository.save(entity);

            mailService.sendSosNotification(
                    "gad.ntezimana@gmail.com",
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
            SOSRequest entity = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("SOSRequest not found"));

            entity.setStatus(dto.getStatus());
            entity.setLatitude(dto.getLatitude());
            entity.setLongitude(dto.getLongitude());

            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
            entity.setUser(user);

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
