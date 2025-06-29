// src/pages/dashboard/UserDashboard.jsx
import React from "react";
import { useNavigate } from "react-router-dom";

function UserDashboard() {
  const navigate = useNavigate();

  return (
    <div>
      <h2>Welcome to Your Dashboard 👋</h2>
      <ul>
        <li>
          <button onClick={() => navigate("/create")}>
            🚨 Create SOS Request
          </button>
        </li>
        <li>
          <button onClick={() => navigate("/nearby")}>
            📍 View Nearby Emergency Services
          </button>
        </li>
        <li>
          <button onClick={() => navigate("/tips")}>
            📘 View Emergency Tips
          </button>
        </li>
      </ul>
    </div>
  );
}

export default UserDashboard;
