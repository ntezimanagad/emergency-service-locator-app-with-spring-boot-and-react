package com.emergence.locator.app.emergence.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emergence.locator.app.emergence.model.EmergencyService;

import java.util.List;

public interface EmergencyServiceRepository extends JpaRepository<EmergencyService, Long> {

    @Query(value = """
        SELECT *,
               (6371000 * acos(
                 cos(radians(:lat)) * cos(radians(latitude)) *
                 cos(radians(longitude) - radians(:lng)) +
                 sin(radians(:lat)) * sin(radians(latitude))
               )) AS distance
        FROM emergency_service
        HAVING distance <= :radiusInMeters
        ORDER BY distance
        """, nativeQuery = true)
    List<EmergencyService> findNearby(
        @Param("lat") double lat,
        @Param("lng") double lng,
        @Param("radiusInMeters") double radiusInMeters
    );
}
