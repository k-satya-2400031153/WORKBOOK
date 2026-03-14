import React, { useState } from 'react';
import './StudentManager.css';

const StudentManager = () => {
    const initialStudents = [
        { id: '101', name: 'Alice Smith', course: 'Computer Networks' },
        { id: '102', name: 'Bob Johnson', course: 'Full Stack Development' },
        { id: '103', name: 'Charlie Brown', course: 'Data Science' },
        { id: '104', name: 'Diana Prince', course: 'Artificial Intelligence' },
        { id: '105', name: 'Evan Wright', course: 'Cyber Security' }
    ];

    const [students, setStudents] = useState(initialStudents);
    const [newStudent, setNewStudent] = useState({ id: '', name: '', course: '' });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewStudent({ ...newStudent, [name]: value });
    };

    const handleAddStudent = () => {
        if (!newStudent.id || !newStudent.name || !newStudent.course) return;
        setStudents([...students, newStudent]);
        setNewStudent({ id: '', name: '', course: '' });
    };

    // Step 7: Create delete logic
    const handleDeleteStudent = (idToDelete) => {
        // 7i & 7ii: Filter out the student to be deleted and update the UI immediately
        const updatedStudents = students.filter(student => student.id !== idToDelete);
        setStudents(updatedStudents);
    };

    return (
        <div className="container">
            <h2>Academic Portal - Student Manager</h2>

            <div className="form-container">
                <input
                    type="text"
                    name="id"
                    placeholder="Enter ID"
                    value={newStudent.id}
                    onChange={handleInputChange}
                />
                <input
                    type="text"
                    name="name"
                    placeholder="Enter Name"
                    value={newStudent.name}
                    onChange={handleInputChange}
                />
                <input
                    type="text"
                    name="course"
                    placeholder="Enter Course"
                    value={newStudent.course}
                    onChange={handleInputChange}
                />
                <button onClick={handleAddStudent} className="add-btn">Add Student</button>
            </div>

            {/* Step 8: If the list is empty, display the message */}
            {students.length === 0 ? (
                <p className="empty-message">No students available</p>
            ) : (
                /* Step 6: Display the list of students */
                <table className="student-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Course</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {students.map((student) => (
                        <tr key={student.id}>
                            <td>{student.id}</td>
                            <td>{student.name}</td>
                            <td>{student.course}</td>
                            <td>
                                {/* Step 7: Delete button for each row */}
                                <button
                                    onClick={() => handleDeleteStudent(student.id)}
                                    className="delete-btn"
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default StudentManager;