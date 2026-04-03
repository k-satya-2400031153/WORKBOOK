import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Profile() {
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const userId = localStorage.getItem('userId');
    if (!userId) {
      navigate('/login');
      return;
    }
    axios.get(`http://localhost:8080/profile/${userId}`)
      .then((res) => {
        setProfile(res.data);
      })
      .catch((err) => {
        setError(err.response?.data?.error || 'Failed to load profile.');
      })
      .finally(() => setLoading(false));
  }, [navigate]);

  if (loading) {
    return (
      <div className="page-container">
        <div className="card">
          <p className="loading-text">⏳ Loading profile...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="page-container">
      <div className="card profile-card">
        {error ? (
          <div className="alert alert-error">{error}</div>
        ) : (
          <>
            <div className="profile-avatar">
              {profile?.username?.charAt(0).toUpperCase()}
            </div>
            <h2 className="card-title">Your Profile</h2>
            <div className="profile-details">
              <div className="profile-field">
                <span className="field-label">User ID</span>
                <span className="field-value">#{profile?.id}</span>
              </div>
              <div className="profile-field">
                <span className="field-label">Username</span>
                <span className="field-value">{profile?.username}</span>
              </div>
              <div className="profile-field">
                <span className="field-label">Email</span>
                <span className="field-value">{profile?.email}</span>
              </div>
            </div>
            <button className="btn-secondary" onClick={() => navigate('/home')}>
              ← Back to Home
            </button>
          </>
        )}
      </div>
    </div>
  );
}

export default Profile;
