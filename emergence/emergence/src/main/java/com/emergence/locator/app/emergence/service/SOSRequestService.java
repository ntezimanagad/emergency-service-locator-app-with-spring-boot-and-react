package com.emergence.locator.app.emergence.service;

import java.util.List;

import com.emergence.locator.app.emergence.dto.SOSRequestDTO;

public interface SOSRequestService {
    List<SOSRequestDTO> getAll();
    SOSRequestDTO getById(Long id);
    SOSRequestDTO save(SOSRequestDTO dto);
    SOSRequestDTO update(Long id, SOSRequestDTO dto);
    void delete(Long id);
}
