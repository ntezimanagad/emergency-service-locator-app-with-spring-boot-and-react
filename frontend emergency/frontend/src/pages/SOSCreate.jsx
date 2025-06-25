// src/pages/sos/SOSCreate.jsx
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
  const token = localStorage.getItem("token");

  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_API_KEY,
  });

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
      }
    );
  }, []);

  const handleSubmit = async () => {
    if (!position) return;
    try {
      const res = await axios.post(
        "http://localhost:8080/api/sos/create",
        {
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
      setMessage("SOS request sent successfully.");
    } catch (err) {
      console.error(err);
      setMessage("Failed to send SOS.");
    }
  };

  if (!isLoaded || !position) return <p>Loading map...</p>;

  return (
    <div>
      <h2>Create SOS Request</h2>
      <GoogleMap
        mapContainerStyle={containerStyle}
        center={position}
        zoom={14}
      >
        <Marker position={position} />
      </GoogleMap>

      <div>
        <label>Status:</label>
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
