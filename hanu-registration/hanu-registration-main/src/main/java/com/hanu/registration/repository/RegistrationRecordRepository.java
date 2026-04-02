package com.hanu.registration.repository;

import com.hanu.registration.model.RegistrationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistrationRecordRepository extends JpaRepository<RegistrationRecord, Long> {
    List<RegistrationRecord> findByStudentId(String studentId);
    RegistrationRecord findByStudentIdAndCourseId(String studentId, Long courseId);
}