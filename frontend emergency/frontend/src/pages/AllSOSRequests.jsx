// src/pages/sos/AllSOSRequests.jsx
import React, { useEffect, useState } from "react";
import axios from "axios";

function AllSOSRequests() {
  const [requests, setRequests] = useState([]);
  const [error, setError] = useState("");
  const token = localStorage.getItem("token");

  useEffect(() => {
    fetchRequests();
  }, []);

  const fetchRequests = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/sos/get", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setRequests(res.data);
    } catch (err) {
      console.error(err);
      setError("Failed to fetch SOS requests.");
    }
  };

  return (
    <div>
      <h2>📂 All SOS Requests</h2>
      {error && <p>{error}</p>}
      {requests.length === 0 ? (
        <p>No SOS requests found.</p>
      ) : (
        <table border="1" cellPadding="8">
          <thead>
            <tr>
              <th>ID</th>
              <th>User ID</th>
              <th>Status</th>
              <th>Latitude</th>
              <th>Longitude</th>
              <th>Timestamp</th>
            </tr>
          </thead>
          <tbody>
            {requests.map((req) => (
              <tr key={req.id}>
                <td>{req.id}</td>
                <td>{req.userId}</td>
                <td>{req.status}</td>
                <td>{req.latitude}</td>
                <td>{req.longitude}</td>
                <td>{req.timestamp}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default AllSOSRequests;
