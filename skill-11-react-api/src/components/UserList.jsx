import React, { useState, useEffect } from 'react';

const UserList = () => {
    // State variables for data, loading, and errors
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Fetching from the public JSONPlaceholder API
        fetch('https://jsonplaceholder.typicode.com/users')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Failed to fetch API users');
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
    }, []);

    // Display loading or error messages if necessary
    if (loading) return <div>Loading API users...</div>;
    if (error) return <div style={{ color: 'red' }}>Error: {error}</div>;

    return (
        <div>
            <h2>Part B: Users API (JSONPlaceholder)</h2>
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

export default UserList;