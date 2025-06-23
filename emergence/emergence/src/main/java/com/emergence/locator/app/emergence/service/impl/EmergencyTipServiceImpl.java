package com.emergence.locator.app.emergence.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.emergence.locator.app.emergence.dto.EmergencyTipDTO;
import com.emergence.locator.app.emergence.mapper.EmergencyTipMapper;
import com.emergence.locator.app.emergence.model.EmergencyTip;
import com.emergence.locator.app.emergence.repository.EmergencyTipRepository;
import com.emergence.locator.app.emergence.service.EmergencyTipService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmergencyTipServiceImpl implements EmergencyTipService {

    private static final Logger log = LoggerFactory.getLogger(EmergencyTipServiceImpl.class);

    private final EmergencyTipRepository repository;

    public EmergencyTipServiceImpl(EmergencyTipRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EmergencyTipDTO> getAll() {
        try {
            log.info("Fetching all emergency tips...");
            return repository.findAll()
                    .stream()
                    .map(EmergencyTipMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to fetch emergency tips", e);
            throw new RuntimeException("Unable to fetch emergency tips");
        }
    }

    @Override
    public EmergencyTipDTO getById(Long id) {
        try {
            Optional<EmergencyTip> optional = repository.findById(id);
            if (optional.isEmpty()) {
                throw new RuntimeException("Emergency tip not found with id: " + id);
            }
            return EmergencyTipMapper.toDTO(optional.get());
        } catch (Exception e) {
            log.error("Failed to fetch emergency tip by id {}", id, e);
            throw new RuntimeException("Emergency tip lookup failed");
        }
    }

    @Override
    public EmergencyTipDTO save(EmergencyTipDTO dto) {
        try {
            EmergencyTip entity = EmergencyTipMapper.toEntity(dto);
            EmergencyTip saved = repository.save(entity);
            log.info("Saved emergency tip with id: {}", saved.getId());
            return EmergencyTipMapper.toDTO(saved);
        } catch (Exception e) {
            log.error("Failed to save emergency tip", e);
            throw new RuntimeException("Save emergency tip failed");
        }
    }

    @Override
    public EmergencyTipDTO update(Long id, EmergencyTipDTO dto) {
        try {
            Optional<EmergencyTip> optional = repository.findById(id);
            if (optional.isEmpty()) {
                throw new RuntimeException("Emergency tip not found");
            }
            EmergencyTip entity = optional.get();
            entity.setTitle(dto.getTitle());
            //entity.setDescription(dto.getDescription());
            EmergencyTip updated = repository.save(entity);
            log.info("Updated emergency tip with id: {}", id);
            return EmergencyTipMapper.toDTO(updated);
        } catch (Exception e) {
            log.error("Failed to update emergency tip", e);
            throw new RuntimeException("Update emergency tip failed");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                log.info("Deleted emergency tip with id: {}", id);
            } else {
                throw new RuntimeException("Emergency tip not found");
            }
        } catch (Exception e) {
            log.error("Failed to delete emergency tip", e);
            throw new RuntimeException("Delete emergency tip failed");
        }
    }
}
