package com.emergence.locator.app.emergence.mapper;

import java.time.LocalDateTime;

import com.emergence.locator.app.emergence.dto.SOSRequestDTO;
import com.emergence.locator.app.emergence.model.SOSRequest;
import com.emergence.locator.app.emergence.model.User;

public class SOSRequestMapper {

    public static SOSRequestDTO toDTO(SOSRequest request) {
        SOSRequestDTO dto = new SOSRequestDTO();
        dto.setId(request.getId());
        dto.setStatus(request.getStatus());
        dto.setUserId(request.getUser() != null ? request.getUser().getId() : null);
        dto.setLatitude(request.getLatitude());
        dto.setLongitude(request.getLongitude());
        return dto;
    }

    public static SOSRequest toEntity(SOSRequestDTO dto, User user) {
        SOSRequest entity = new SOSRequest();
        entity.setUser(user);
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setStatus(dto.getStatus());
        entity.setTimestamp(LocalDateTime.now());
        return entity;
    }
}
