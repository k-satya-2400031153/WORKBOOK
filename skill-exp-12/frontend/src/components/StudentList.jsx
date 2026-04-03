import React from 'react';
import axios from 'axios';

const StudentList = ({ students, refreshList, setEditingStudent }) => {

    const handleDelete = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/students/${id}`);
            refreshList();
        } catch (error) {
            console.error("Error deleting student", error);
        }
    };

    return (
        <div>
            <h2>Student List</h2>
            <table border="1" cellPadding="10">
                <thead>
                <tr>
                    <th>ID</th><th>Name</th><th>Email</th><th>Course</th><th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {students.map((s) => (
                    <tr key={s.id}>
                        <td>{s.id}</td>
                        <td>{s.name}</td>
                        <td>{s.email}</td>
                        <td>{s.course}</td>
                        <td>
                            <button onClick={() => setEditingStudent(s)}>Update</button>
                            <button onClick={() => handleDelete(s.id)} style={{marginLeft: '10px', color: 'red'}}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default StudentList;