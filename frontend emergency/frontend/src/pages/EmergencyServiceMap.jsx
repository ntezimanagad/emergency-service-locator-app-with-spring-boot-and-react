// src/pages/services/EmergencyServiceMap.jsx
import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  GoogleMap,
  Marker,
  useJsApiLoader,
  InfoWindow,
} from "@react-google-maps/api";

const containerStyle = {
  width: "100%",
  height: "500px",
};

function EmergencyServiceMap() {
  const [userLocation, setUserLocation] = useState(null);
  const [services, setServices] = useState([]);
  const [selected, setSelected] = useState(null);
  const [filter, setFilter] = useState("ALL");

  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: "AIzaSyA-auhtG67pLpOPyoSyrSsX39Wp4glz4CQ",
  });

  useEffect(() => {
    navigator.geolocation.getCurrentPosition(
      (pos) => {
        const loc = {
          lat: pos.coords.latitude,
          lng: pos.coords.longitude,
        };
        setUserLocation(loc);
        fetchNearby(loc.lat, loc.lng);
      },
      (err) => {
        console.error("Location error:", err);
      }
    );
  }, []);

  const fetchNearby = async (lat, lng) => {
    try {
      const res = await axios.get(
        `http://localhost:8080/api/emergency-services/nearby?lat=${lat}&lng=${lng}&radius=5`
      );
      setServices(res.data);
    } catch (err) {
      console.error("Failed to load services", err);
    }
  };

  const filteredServices =
    filter === "ALL"
      ? services
      : services.filter((s) => s.type === filter.toUpperCase());

  if (!isLoaded || !userLocation) return <p>Loading map...</p>;

  return (
    <div>
      <h2>Nearby Emergency Services</h2>

      <div>
        <label>Filter: </label>
        <select value={filter} onChange={(e) => setFilter(e.target.value)}>
          <option value="ALL">All</option>
          <option value="HOSPITAL">Hospital</option>
          <option value="POLICE">Police</option>
          <option value="FIRE">Fire</option>
          <option value="PHARMACY">Pharmacy</option>
        </select>
      </div>

      <GoogleMap
        mapContainerStyle={containerStyle}
        center={userLocation}
        zoom={13}
      >
        {/* User marker */}
        <Marker position={userLocation} label="You" />

        {/* Emergency services */}
        {filteredServices.map((service) => (
          <Marker
            key={service.id}
            position={{ lat: service.latitude, lng: service.longitude }}
            onClick={() => setSelected(service)}
          />
        ))}

        {selected && (
          <InfoWindow
            position={{ lat: selected.latitude, lng: selected.longitude }}
            onCloseClick={() => setSelected(null)}
          >
            <div>
              <h4>{selected.name}</h4>
              <p>Type: {selected.type}</p>
              <p>Phone: {selected.phone}</p>
              <p>Address: {selected.address}</p>
            </div>
          </InfoWindow>
        )}
      </GoogleMap>
    </div>
  );
}

export default EmergencyServiceMap;
