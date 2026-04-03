import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate, Link } from 'react-router-dom';

function Register() {
  const [form, setForm] = useState({ username: '', password: '', email: '' });
  const [message, setMessage] = useState({ text: '', type: '' });
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
    setMessage({ text: '', type: '' });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const { username, password, email } = form;
    if (!username || !password || !email) {
      setMessage({ text: 'Please fill in all fields.', type: 'error' });
      return;
    }
    if (!/\S+@\S+\.\S+/.test(email)) {
      setMessage({ text: 'Please enter a valid email address.', type: 'error' });
      return;
    }
    setLoading(true);
    try {
      await axios.post('http://localhost:8080/register', form);
      setMessage({ text: 'Registration successful! Redirecting to login...', type: 'success' });
      setTimeout(() => navigate('/login'), 1800);
    } catch (err) {
      setMessage({ text: err.response?.data?.error || 'Registration failed.', type: 'error' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page-container">
      <div className="card form-card">
        <div className="card-header">
          <h2 className="card-title">Create Account</h2>
          <p className="card-subtitle">Join us today — it's free!</p>
        </div>
        <form onSubmit={handleSubmit} className="form">
          {message.text && (
            <div className={`alert alert-${message.type}`}>{message.text}</div>
          )}
          <div className="form-group">
            <label htmlFor="reg-username" className="form-label">Username</label>
            <input
              id="reg-username"
              type="text"
              name="username"
              value={form.username}
              onChange={handleChange}
              className="form-input"
              placeholder="Choose a username"
              autoComplete="username"
            />
          </div>
          <div className="form-group">
            <label htmlFor="reg-email" className="form-label">Email</label>
            <input
              id="reg-email"
              type="email"
              name="email"
              value={form.email}
              onChange={handleChange}
              className="form-input"
              placeholder="Enter your email"
              autoComplete="email"
            />
          </div>
          <div className="form-group">
            <label htmlFor="reg-password" className="form-label">Password</label>
            <input
              id="reg-password"
              type="password"
              name="password"
              value={form.password}
              onChange={handleChange}
              className="form-input"
              placeholder="Create a password"
              autoComplete="new-password"
            />
          </div>
          <button type="submit" className="btn-primary" disabled={loading}>
            {loading ? 'Creating account...' : 'Create Account'}
          </button>
        </form>
        <p className="redirect-text">
          Already have an account? <Link to="/login" className="link">Sign in here</Link>
        </p>
      </div>
    </div>
  );
}

export default Register;
