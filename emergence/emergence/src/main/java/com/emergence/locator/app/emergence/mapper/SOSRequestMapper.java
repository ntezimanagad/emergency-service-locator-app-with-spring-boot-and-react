package com.emergence.locator.app.emergence.mapper;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import com.emergence.locator.app.emergence.dto.SOSRequestDTO;
import com.emergence.locator.app.emergence.model.SOSRequest;
import com.emergence.locator.app.emergence.model.User;

public class SOSRequestMapper {
    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public static SOSRequestDTO toDTO(SOSRequest request) {
        SOSRequestDTO dto = new SOSRequestDTO();
        dto.setId(request.getId());
        dto.setStatus(request.getStatus());
        dto.setUserId(request.getUser() != null ? request.getUser().getId() : null);

        Point location = request.getLocation();
        if (location != null) {
            dto.setLatitude(location.getY());
            dto.setLongitude(location.getX());
        }
        return dto;
    }

    public static SOSRequest toEntity(SOSRequestDTO dto, User user) {
        SOSRequest request = new SOSRequest();
        request.setStatus(dto.getStatus());
        request.setUser(user);

        Point point = geometryFactory.createPoint(new Coordinate(dto.getLongitude(), dto.getLatitude()));
        point.setSRID(4326);
        request.setLocation(point);

        return request;
    }
}
