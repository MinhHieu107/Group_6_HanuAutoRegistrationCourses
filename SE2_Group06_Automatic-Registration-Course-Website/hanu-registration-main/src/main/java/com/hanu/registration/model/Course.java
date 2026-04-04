package com.hanu.registration.model;

import jakarta.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String idToHoc; 
    
    private String courseCode;
    private String courseName;
    private String groupName;
    private String subGroup;
    private String lecturer;
    private int credits;
    private int capacity;
    private int enrolled;
    
    @Column(columnDefinition = "TEXT")
    private String scheduleTime;

    public boolean hasSlots() { return enrolled < capacity; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIdToHoc() { return idToHoc; }
    public void setIdToHoc(String idToHoc) { this.idToHoc = idToHoc; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public String getSubGroup() { return subGroup; }
    public void setSubGroup(String subGroup) { this.subGroup = subGroup; }
    public String getLecturer() { return lecturer; }
    public void setLecturer(String lecturer) { this.lecturer = lecturer; }
    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
    public String getScheduleTime() { return scheduleTime; }
    public void setScheduleTime(String scheduleTime) { this.scheduleTime = scheduleTime; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getEnrolled() { return enrolled; }
    public void setEnrolled(int enrolled) { this.enrolled = enrolled; }
}