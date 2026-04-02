package com.hanu.registration.repository;

import com.hanu.registration.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByIdToHoc(String idToHoc);
}