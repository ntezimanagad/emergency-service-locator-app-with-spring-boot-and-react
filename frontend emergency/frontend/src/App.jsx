// src/App.jsx
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import PrivateRoutes from "./component/PrivateRoutes";
import UserDashboard from "./pages/UserDashboard";
import SOSCreate from "./pages/SOSCreate";
import EmergencyTips from "./pages/EmergencyTips";
import EmergencyServiceMap from "./pages/EmergencyServiceMap";
import AdminDashboard from "./pages/AdminDashboard";
import ManageServices from "./pages/ManageServices";
import ManageTips from "./pages/ManageTips";
import AllSOSRequests from "./pages/AllSOSRequests";

// Optional: You can wrap protected routes in auth later

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login/>}/>  
        <Route path="/register" element={<Register/>}/>  
        <Route path="/user" element={<PrivateRoutes role="USER"><UserDashboard/></PrivateRoutes>}/>
        <Route path="/create" element={<PrivateRoutes role="USER"><SOSCreate/></PrivateRoutes>}/>
        <Route path="/tips" element={<PrivateRoutes role="USER"><EmergencyTips/></PrivateRoutes>}/> 
        <Route path="/nearby" element={<PrivateRoutes role="USER"><EmergencyServiceMap/></PrivateRoutes>}/> 
        <Route path="/admin" element={<PrivateRoutes role="ADMIN"><AdminDashboard/></PrivateRoutes>}/>
        <Route path="/manage" element={<PrivateRoutes role="ADMIN"><ManageServices/></PrivateRoutes>}/>
        <Route path="/tipsmanage" element={<PrivateRoutes role="ADMIN"><ManageTips/></PrivateRoutes>}/> 
        <Route path="/all" element={<PrivateRoutes role="ADMIN"><AllSOSRequests/></PrivateRoutes>}/> 

      </Routes> 
    </Router>
  );
}

export default App;
