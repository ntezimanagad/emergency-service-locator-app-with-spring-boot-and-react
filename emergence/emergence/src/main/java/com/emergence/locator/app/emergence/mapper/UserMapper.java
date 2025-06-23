package com.emergence.locator.app.emergence.mapper;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import com.emergence.locator.app.emergence.dto.UserDTO;
import com.emergence.locator.app.emergence.model.User;

public class UserMapper {
    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());

        Point location = user.getLocation();
        if (location != null) {
            dto.setLatitude(location.getY());
            dto.setLongitude(location.getX());
        }
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        Point point = geometryFactory.createPoint(new Coordinate(dto.getLongitude(), dto.getLatitude()));
        point.setSRID(4326);
        user.setLocation(point);

        return user;
    }
}
