// src/pages/dashboard/AdminDashboard.jsx
import React from "react";
import { useNavigate } from "react-router-dom";

function AdminDashboard() {
  const navigate = useNavigate();

  return (
    <div>
      <h2>Admin Dashboard 🛠️</h2>
      <ul>
        <li>
          <button onClick={() => navigate("/manage")}>
            📍 Manage Emergency Services
          </button>
        </li>
        <li>
          <button onClick={() => navigate("/tipsmanage")}>
            📘 Manage Emergency Tips
          </button>
        </li>
        <li>
          <button onClick={() => navigate("/all")}>
            📂 View All SOS Requests
          </button>
        </li>
      </ul>
    </div>
  );
}

export default AdminDashboard;
