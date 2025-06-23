package com.emergence.locator.app.emergence.service;

import java.util.List;

import com.emergence.locator.app.emergence.dto.EmergencyTipDTO;

public interface EmergencyTipService {
    List<EmergencyTipDTO> getAll();
    EmergencyTipDTO getById(Long id);
    EmergencyTipDTO save(EmergencyTipDTO dto);
    EmergencyTipDTO update(Long id, EmergencyTipDTO dto);
    void delete(Long id);
}
