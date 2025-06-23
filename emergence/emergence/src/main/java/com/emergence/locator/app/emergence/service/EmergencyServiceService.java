package com.emergence.locator.app.emergence.service;

import java.util.List;

import com.emergence.locator.app.emergence.dto.EmergencyServiceDTO;
import com.emergence.locator.app.emergence.model.ServiceType;

public interface EmergencyServiceService {
    List<EmergencyServiceDTO> getAll();
    EmergencyServiceDTO save(EmergencyServiceDTO dto);
    EmergencyServiceDTO update(Long id, EmergencyServiceDTO dto);
    void delete(Long id);
    List<EmergencyServiceDTO> findNearby(ServiceType type, double latitude, double longitude, double radiusMeters);
}
