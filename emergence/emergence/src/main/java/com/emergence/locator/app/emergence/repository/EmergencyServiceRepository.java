package com.emergence.locator.app.emergence.repository;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emergence.locator.app.emergence.model.EmergencyService;
import com.emergence.locator.app.emergence.model.ServiceType;

import java.util.List;

public interface EmergencyServiceRepository extends JpaRepository<EmergencyService, Long> {

    // Find by type within distance (using PostGIS ST_DWithin)
    @Query(value = "SELECT e FROM EmergencyService e WHERE e.type = :type AND " +
            "ST_DWithin(e.location, :point, :distance) = true")
    List<EmergencyService> findByTypeNearLocation(
            @Param("type") ServiceType type,
            @Param("point") Point point,
            @Param("distance") double distanceInMeters);

        
    @Query(value = """
        SELECT es FROM EmergencyService es
        WHERE ST_DWithin(es.location, :point, :radiusInMeters) = true
    """)
    List<EmergencyService> findNearby(@Param("point") Point point, @Param("radiusInMeters") double radiusInMeters);

}
