// src/pages/tips/ManageTips.jsx
import React, { useEffect, useState } from "react";
import axios from "axios";

function ManageTips() {
  const [tips, setTips] = useState([]);
  const [title, setTitle] = useState("");
  const [category, setCategory] = useState("GENERAL");
  const [content, setContent] = useState("");
  const [message, setMessage] = useState("");
  const token = localStorage.getItem("token");

  const fetchTips = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/tips/get",
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setTips(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    fetchTips();
  }, []);

  const handleCreate = async () => {
    try {
      await axios.post(
        "http://localhost:8080/api/tips/create",
        { title, category, content },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      setMessage("Tip added successfully");
      setTitle("");
      setCategory("GENERAL");
      setContent("");
      fetchTips(); // reload
    } catch (err) {
      console.error(err);
      setMessage("Failed to add tip");
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/tips/delete/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setMessage("Tip deleted");
      fetchTips();
    } catch (err) {
      console.error(err);
      setMessage("Failed to delete tip");
    }
  };

  return (
    <div>
      <h2>🛠 Manage Emergency Tips</h2>

      <div>
        <input
          type="text"
          placeholder="Title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <select value={category} onChange={(e) => setCategory(e.target.value)}>
          <option value="GENERAL">GENERAL</option>
          <option value="FIRE">FIRE</option>
          <option value="MEDICAL">MEDICAL</option>
          <option value="SECURITY">SECURITY</option>
        </select>
        <textarea
          placeholder="Content"
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
        <button onClick={handleCreate}>➕ Add Tip</button>
      </div>

      {message && <p>{message}</p>}

      <div>
        <h3>Existing Tips</h3>
        {tips.map((tip) => (
          <div key={tip.id} style={{ border: "1px solid #ccc", margin: "1rem", padding: "1rem" }}>
            <h4>{tip.title}</h4>
            <p>{tip.content}</p>
            <p><strong>Category:</strong> {tip.category}</p>
            <button onClick={() => handleDelete(tip.id)}>🗑 Delete</button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default ManageTips;
