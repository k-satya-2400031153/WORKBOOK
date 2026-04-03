import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AddStudent = ({ refreshList, editingStudent, setEditingStudent }) => {
    const [student, setStudent] = useState({ name: '', email: '', course: '' });

    useEffect(() => { if (editingStudent) setStudent(editingStudent); }, [editingStudent]);

    const handleChange = (e) => setStudent({ ...student, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (editingStudent) {
                await axios.put(`/students/${editingStudent.id}`, student);
                setEditingStudent(null);
            } else {
                await axios.post('/students', student);
            }
            setStudent({ name: '', email: '', course: '' });
            refreshList();
        } catch (error) { console.error("Error saving", error); }
    };

    return (
        <div>
            <h2>{editingStudent ? 'Update Student' : 'Add Student'}</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" name="name" placeholder="Name" value={student.name} onChange={handleChange} required />
                <input type="email" name="email" placeholder="Email" value={student.email} onChange={handleChange} required />
                <input type="text" name="course" placeholder="Course" value={student.course} onChange={handleChange} required />
                <button type="submit">{editingStudent ? 'Update' : 'Submit'}</button>
            </form>
        </div>
    );
};
export default AddStudent;