import React, { useEffect, useState } from "react";
import axios from "axios";
import { GoogleMap, Marker, useJsApiLoader } from "@react-google-maps/api";

const containerStyle = {
  width: "100%",
  height: "400px",
};

function SOSCreate() {
  const [position, setPosition] = useState(null);
  const [status, setStatus] = useState("PENDING");
  const [message, setMessage] = useState("");
  const [userId, setUserId] = useState(null);
  const token = localStorage.getItem("token");

  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_API_KEY,
  });

  // Get user ID
  useEffect(() => {
    const fetchUserId = async () => {
      try {
        const res = await axios.get("http://localhost:8080/api/sos/me", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setUserId(res.data.id);
      } catch (err) {
        console.error("Failed to fetch user ID:", err);
        setMessage("User info could not be retrieved.");
      }
    };

    fetchUserId();
  }, [token]);

  // Get current location
  useEffect(() => {
    navigator.geolocation.getCurrentPosition(
      (pos) => {
        setPosition({
          lat: pos.coords.latitude,
          lng: pos.coords.longitude,
        });
      },
      (err) => {
        console.error("Geolocation error:", err);
        setMessage("Could not get current location.");
      }
    );
  }, []);

  // Handle SOS creation
  const handleSubmit = async () => {
    if (!position || !userId) return;
    try {
      await axios.post(
        "http://localhost:8080/api/sos/create",
        {
          userId,
          latitude: position.lat,
          longitude: position.lng,
          status,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setMessage("✅ SOS request sent successfully.");
    } catch (err) {
      console.error(err);
      setMessage("❌ Failed to send SOS.");
    }
  };

  // Allow user to click on map to select position
  const handleMapClick = (event) => {
    setPosition({
      lat: event.latLng.lat(),
      lng: event.latLng.lng(),
    });
  };

  if (!isLoaded || !position) return <p>Loading map...</p>;

  return (
    <div>
      <h2>📍 Create SOS Request</h2>

      <GoogleMap
        mapContainerStyle={containerStyle}
        center={position}
        zoom={14}
        onClick={handleMapClick}
      >
        <Marker position={position} />
      </GoogleMap>

      <p><strong>Selected Location:</strong> Lat: {position.lat.toFixed(5)} | Lng: {position.lng.toFixed(5)}</p>

      <div>
        <label>Status: </label>
        <select value={status} onChange={(e) => setStatus(e.target.value)}>
          <option value="PENDING">PENDING</option>
          <option value="URGENT">URGENT</option>
        </select>
      </div>

      <button onClick={handleSubmit}>🚨 Send SOS</button>
      {message && <p>{message}</p>}
    </div>
  );
}

export default SOSCreate;
