package com.emergence.locator.app.emergence.mapper;

import com.emergence.locator.app.emergence.dto.UserDTO;
import com.emergence.locator.app.emergence.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());

        dto.setLatitude(user.getLatitude());
        dto.setLongitude(user.getLongitude());

        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        user.setLatitude(dto.getLatitude());
        user.setLongitude(dto.getLongitude());

        return user;
    }
}
