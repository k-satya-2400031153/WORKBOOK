import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// Importing all the components we built
import Dashboard from './components/Dashboard';
import LocalUserList from './components/LocalUserList';
import UserList from './components/UserList';
import FakePostList from './components/FakePostList';

const App = () => {
  return (
      // Router wraps the entire app to enable hyperlink navigation
      <Router>
        <div style={{ fontFamily: 'Segoe UI, Tahoma, Geneva, Verdana, sans-serif', maxWidth: '900px', margin: '40px auto' }}>

          {/* The Dashboard stays at the top of the screen at all times */}
          <Dashboard />

          {/* The Routes determine which component to show below the Dashboard based on the URL */}
          <div style={{ padding: '20px', border: '1px solid #ddd', borderRadius: '8px' }}>
            <Routes>
              <Route path="/" element={<p>Welcome! Select an option from the dashboard above.</p>} />
              <Route path="/local-users" element={<LocalUserList />} />
              <Route path="/api-users" element={<UserList />} />
              <Route path="/fake-posts" element={<FakePostList />} />
            </Routes>
          </div>

        </div>
      </Router>
  );
};

export default App;