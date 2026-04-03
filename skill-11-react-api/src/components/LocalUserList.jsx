import React, { useState, useEffect } from 'react';

const LocalUserList = () => {
    // State to hold our data, loading status, and any errors
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Fetching from the public folder
        fetch('/users.json')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to fetch local users');
                }
                return response.json();
            })
            .then((data) => {
                setUsers(data);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    }, []); // Empty dependency array ensures this runs only once when the component mounts

    // Handling loading and error states before rendering the main UI
    if (loading) return <div>Loading local users...</div>;
    if (error) return <div style={{ color: 'red' }}>Error: {error}</div>;

    return (
        <div>
            <h2>Part A: Local Users</h2>
            <ul>
                {users.map((user) => (
                    <li key={user.id} style={{ marginBottom: '10px' }}>
                        <strong>{user.name}</strong> <br />
                        Email: {user.email} <br />
                        Phone: {user.phone}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default LocalUserList;