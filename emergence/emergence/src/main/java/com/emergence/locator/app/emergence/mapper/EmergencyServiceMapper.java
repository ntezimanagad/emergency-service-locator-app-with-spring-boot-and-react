package com.emergence.locator.app.emergence.mapper;

import com.emergence.locator.app.emergence.dto.EmergencyServiceDTO;
import com.emergence.locator.app.emergence.model.EmergencyService;
import com.emergence.locator.app.emergence.model.ServiceType;

public class EmergencyServiceMapper {

    public static EmergencyServiceDTO toDTO(EmergencyService service) {
        EmergencyServiceDTO dto = new EmergencyServiceDTO();
        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setType(service.getType() != null ? service.getType().name() : null);
        dto.setPhone(service.getPhone());
        dto.setAddress(service.getAddress());
        dto.setLatitude(service.getLatitude());
        dto.setLongitude(service.getLongitude());
        return dto;
    }

    public static EmergencyService toEntity(EmergencyServiceDTO dto) {
        EmergencyService service = new EmergencyService();
        service.setName(dto.getName());
        service.setType(dto.getType() != null ? ServiceType.valueOf(dto.getType()) : null);
        service.setPhone(dto.getPhone());
        service.setAddress(dto.getAddress());
        service.setLatitude(dto.getLatitude());
        service.setLongitude(dto.getLongitude());
        return service;
    }
}
