import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Home() {
  const username = localStorage.getItem('username') || 'User';
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login');
  };

  return (
    <div className="page-container">
      <div className="card home-card">
        <div className="home-avatar">
          {username.charAt(0).toUpperCase()}
        </div>
        <h2 className="card-title">Welcome, {username}! 👋</h2>
        <p className="card-subtitle">You are successfully logged in.</p>
        <div className="home-actions">
          <Link to="/profile" className="btn-secondary">View Profile</Link>
          <button className="btn-danger" onClick={handleLogout}>Logout</button>
        </div>
        <div className="home-info">
          <div className="info-item">
            <span className="info-icon">🔒</span>
            <span>Session is active</span>
          </div>
          <div className="info-item">
            <span className="info-icon">💾</span>
            <span>User ID stored securely in localStorage</span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
