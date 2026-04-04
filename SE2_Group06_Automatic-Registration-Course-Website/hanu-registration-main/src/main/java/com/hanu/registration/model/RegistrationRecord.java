package com.hanu.registration.model;

import jakarta.persistence.*;

@Entity
public class RegistrationRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentId;
    
    @ManyToOne
    private Course course;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}