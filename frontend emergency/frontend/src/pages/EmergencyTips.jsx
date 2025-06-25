// src/pages/tips/EmergencyTips.jsx
import React, { useEffect, useState } from "react";
import axios from "axios";

function EmergencyTips() {
  const [tips, setTips] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchTips();
  }, []);

  const fetchTips = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/emergency-tips");
      setTips(res.data);
    } catch (err) {
      console.error(err);
      setError("Failed to load tips");
    }
  };

  return (
    <div>
      <h2>📘 Emergency Tips</h2>
      {error && <p>{error}</p>}
      {tips.length === 0 ? (
        <p>No tips available.</p>
      ) : (
        tips.map((tip) => (
          <div key={tip.id} style={{ border: "1px solid #ccc", margin: "1rem", padding: "1rem" }}>
            <h3>{tip.title}</h3>
            <p><strong>Category:</strong> {tip.category}</p>
            <p>{tip.content}</p>
          </div>
        ))
      )}
    </div>
  );
}

export default EmergencyTips;
