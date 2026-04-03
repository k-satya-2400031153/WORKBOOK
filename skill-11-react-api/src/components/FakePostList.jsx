import React, { useState, useEffect } from 'react';
import axios from 'axios';

const FakePostList = () => {
    const [posts, setPosts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [selectedUserId, setSelectedUserId] = useState(''); // New state for the filter

    const fetchPosts = () => {
        setLoading(true);
        setError(null);

        axios.get('https://dummyjson.com/posts')
            .then((response) => {
                setPosts(response.data.posts);
                setLoading(false);
            })
            .catch((err) => {
                setError(err.message);
                setLoading(false);
            });
    };

    useEffect(() => {
        fetchPosts();
    }, []);

    // Extract unique user IDs from the fetched posts to populate the dropdown options
    const uniqueUserIds = [...new Set(posts.map(post => post.userId))].sort((a, b) => a - b);

    // Filter posts based on the selected dropdown value. If empty, show all.
    const filteredPosts = selectedUserId
        ? posts.filter(post => post.userId.toString() === selectedUserId)
        : posts;

    return (
        <div>
            <h2>Part C: Fake API Posts</h2>

            <div style={{ display: 'flex', gap: '15px', marginBottom: '20px', alignItems: 'center' }}>
                <button
                    onClick={fetchPosts}
                    style={{ padding: '8px 16px', cursor: 'pointer', backgroundColor: '#28a745', color: 'white', border: 'none', borderRadius: '4px' }}
                >
                    Refresh Data
                </button>

                {/* Task 6: Dropdown Filter */}
                <div>
                    <label htmlFor="user-filter" style={{ marginRight: '10px', fontWeight: 'bold' }}>Filter by User ID:</label>
                    <select
                        id="user-filter"
                        value={selectedUserId}
                        onChange={(e) => setSelectedUserId(e.target.value)}
                        style={{ padding: '6px', borderRadius: '4px' }}
                    >
                        <option value="">All Users</option>
                        {uniqueUserIds.map(id => (
                            <option key={id} value={id}>User {id}</option>
                        ))}
                    </select>
                </div>
            </div>

            {loading && <div>Loading posts from Fake API...</div>}
            {error && <div style={{ color: 'red' }}>Error: {error}</div>}

            {!loading && !error && (
                <ul>
                    {filteredPosts.map((post) => (
                        <li key={post.id} style={{ marginBottom: '20px', borderBottom: '1px solid #ccc', paddingBottom: '10px' }}>
                            <strong>{post.title}</strong> <span style={{color: '#666'}}>(User ID: {post.userId})</span>
                            <p>{post.body}</p>
                        </li>
                    ))}
                    {filteredPosts.length === 0 && <li>No posts found for this user.</li>}
                </ul>
            )}
        </div>
    );
};

export default FakePostList;