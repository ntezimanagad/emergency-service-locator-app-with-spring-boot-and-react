package com.emergence.locator.app.emergence.mapper;

import com.emergence.locator.app.emergence.dto.EmergencyTipDTO;
import com.emergence.locator.app.emergence.model.EmergencyTip;

public class EmergencyTipMapper {

    public static EmergencyTipDTO toDTO(EmergencyTip tip) {
        EmergencyTipDTO dto = new EmergencyTipDTO();
        dto.setId(tip.getId());
        dto.setTitle(tip.getTitle());
        dto.setContent(tip.getContent());
        dto.setCategory(tip.getCategory());
        return dto;
    }

    public static EmergencyTip toEntity(EmergencyTipDTO dto) {
        EmergencyTip tip = new EmergencyTip();
        tip.setTitle(dto.getTitle());
        tip.setContent(dto.getContent());
        tip.setCategory(dto.getCategory());
        return tip;
    }
}
