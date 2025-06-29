import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  GoogleMap,
  Marker,
  useJsApiLoader,
} from "@react-google-maps/api";

const mapContainerStyle = {
  width: "100%",
  height: "400px",
};

const center = {
  lat: -1.95,  // Kigali center or your default
  lng: 30.06,
};

function ManageServices() {
  const [services, setServices] = useState([]);
  const [name, setName] = useState("");
  const [type, setType] = useState("HOSPITAL");
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [latitude, setLatitude] = useState("");
  const [longitude, setLongitude] = useState("");
  const [message, setMessage] = useState("");

  const token = localStorage.getItem("token");

  const { isLoaded } = useJsApiLoader({
      googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_API_KEY,
    });

  const fetchServices = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/emergency-services/get", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setServices(res.data);
    } catch (err) {
      console.error("Failed to load services", err);
    }
  };

  useEffect(() => {
    fetchServices();
  }, []);

  const handleAdd = async () => {
    try {
      await axios.post(
        "http://localhost:8080/api/emergency-services/create",
        {
          name,
          type,
          phone,
          address,
          latitude: parseFloat(latitude),
          longitude: parseFloat(longitude),
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setMessage("Service added successfully");
      setName("");
      setType("HOSPITAL");
      setPhone("");
      setAddress("");
      setLatitude("");
      setLongitude("");
      fetchServices();
    } catch (err) {
      console.error("Failed to add service", err);
      setMessage("Failed to add service");
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/emergency-services/delete/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setMessage("Service deleted");
      fetchServices();
    } catch (err) {
      console.error("Failed to delete", err);
      setMessage("Failed to delete");
    }
  };

  const handleMapClick = (event) => {
    const lat = event.latLng.lat();
    const lng = event.latLng.lng();
    setLatitude(lat.toFixed(6));
    setLongitude(lng.toFixed(6));
  };

  return (
    <div>
      <h2>🛠 Manage Emergency Services</h2>

      <div>
        <input value={name} onChange={(e) => setName(e.target.value)} placeholder="Name" />
        <select value={type} onChange={(e) => setType(e.target.value)}>
          <option value="HOSPITAL">Hospital</option>
          <option value="POLICE">Police</option>
          <option value="FIRE">Fire</option>
          <option value="PHARMACY">Pharmacy</option>
        </select>
        <input value={phone} onChange={(e) => setPhone(e.target.value)} placeholder="Phone" />
        <input value={address} onChange={(e) => setAddress(e.target.value)} placeholder="Address" />
        <input value={latitude} onChange={(e) => setLatitude(e.target.value)} placeholder="Latitude" />
        <input value={longitude} onChange={(e) => setLongitude(e.target.value)} placeholder="Longitude" />
        <button onClick={handleAdd}>➕ Add Service</button>
      </div>

      {isLoaded && (
        <div style={{ margin: "1rem 0" }}>
          <GoogleMap
            mapContainerStyle={mapContainerStyle}
            center={center}
            zoom={13}
            onClick={handleMapClick}
          >
            {latitude && longitude && (
              <Marker position={{ lat: parseFloat(latitude), lng: parseFloat(longitude) }} />
            )}
          </GoogleMap>
        </div>
      )}

      {message && <p>{message}</p>}

      <div>
        <h3>Existing Services</h3>
        {services.map((s) => (
          <div key={s.id} style={{ border: "1px solid #ccc", margin: "1rem", padding: "1rem" }}>
            <p><strong>{s.name}</strong> ({s.type})</p>
            <p>📞 {s.phone}</p>
            <p>📍 {s.address}</p>
            <button onClick={() => handleDelete(s.id)}>🗑 Delete</button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ManageServices;
