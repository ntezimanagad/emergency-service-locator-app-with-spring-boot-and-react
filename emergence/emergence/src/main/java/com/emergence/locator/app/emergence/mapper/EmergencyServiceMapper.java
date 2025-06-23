package com.emergence.locator.app.emergence.mapper;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import com.emergence.locator.app.emergence.dto.EmergencyServiceDTO;
import com.emergence.locator.app.emergence.model.EmergencyService;
import com.emergence.locator.app.emergence.model.ServiceType;

public class EmergencyServiceMapper {
    private static final GeometryFactory geometryFactory = new GeometryFactory();

    public static EmergencyServiceDTO toDTO(EmergencyService service) {
        EmergencyServiceDTO dto = new EmergencyServiceDTO();
        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setType(service.getType().name());
        dto.setPhone(service.getPhone());
        dto.setAddress(service.getAddress());
        dto.setLatitude(service.getLocation().getY());
        dto.setLongitude(service.getLocation().getX());
        return dto;
    }

    public static EmergencyService toEntity(EmergencyServiceDTO dto) {
        EmergencyService service = new EmergencyService();
        service.setName(dto.getName());
        service.setType(ServiceType.valueOf(dto.getType()));
        service.setPhone(dto.getPhone());
        service.setAddress(dto.getAddress());

        Point point = geometryFactory.createPoint(new Coordinate(dto.getLongitude(), dto.getLatitude()));
        point.setSRID(4326);
        service.setLocation(point);

        return service;
    }
}
