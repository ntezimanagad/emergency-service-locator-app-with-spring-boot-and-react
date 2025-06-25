// src/pages/services/ManageServices.jsx
import React, { useEffect, useState } from "react";
import axios from "axios";

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

  const fetchServices = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/emergency-services");
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
        "http://localhost:8080/api/emergency-services",
        {
          name,
          type,
          phone,
          address,
          latitude: parseFloat(latitude),
          longitude: parseFloat(longitude),
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
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
      await axios.delete(`http://localhost:8080/api/emergency-services/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setMessage("Service deleted");
      fetchServices();
    } catch (err) {
      console.error("Failed to delete", err);
      setMessage("Failed to delete");
    }
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
