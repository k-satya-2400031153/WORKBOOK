import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AddStudent from './components/AddStudent';
import StudentList from './components/StudentList';

function App() {
  const [students, setStudents] = useState([]);
  const [editingStudent, setEditingStudent] = useState(null);

  const fetchStudents = async () => {
    try {
      const response = await axios.get('/students');
      setStudents(response.data);
    } catch (error) { console.error("Error fetching", error); }
  };

  useEffect(() => { fetchStudents(); }, []);

  return (
      <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
        <h1>Production Deployed Student Manager</h1>
        <AddStudent refreshList={fetchStudents} editingStudent={editingStudent} setEditingStudent={setEditingStudent} />
        <br />
        <StudentList students={students} refreshList={fetchStudents} setEditingStudent={setEditingStudent} />
      </div>
  );
}
export default App;