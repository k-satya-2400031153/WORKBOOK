import React from 'react';
import { Link } from 'react-router-dom';

const Dashboard = () => {
    return (
        <div style={{ padding: '20px', backgroundColor: '#f0f4f8', borderRadius: '8px', marginBottom: '20px' }}>
            <h1 style={{ marginTop: 0 }}>FSAD Dashboard</h1>
            <nav style={{ display: 'flex', gap: '15px' }}>
                {/* These Links act as our hyperlinks to navigate between components */}
                <Link to="/local-users" style={linkStyle}>Local Users</Link>
                <Link to="/api-users" style={linkStyle}>Users API</Link>
                <Link to="/fake-posts" style={linkStyle}>Fake API Posts</Link>
            </nav>
        </div>
    );
};

// Some basic CSS styling applied directly as an object (Task 7 partial fulfillment)
const linkStyle = {
    padding: '10px 15px',
    backgroundColor: '#0056b3',
    color: '#ffffff',
    textDecoration: 'none',
    borderRadius: '4px',
    fontWeight: 'bold'
};

export default Dashboard;