package com.hanu.registration.service;

import com.hanu.registration.model.Course;
import com.hanu.registration.model.RegistrationRecord;
import com.hanu.registration.repository.CourseRepository;
import com.hanu.registration.repository.RegistrationRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private CourseRepository courseRepo;
    @Autowired
    private RegistrationRecordRepository recordRepo;
    @Autowired
    private HanuScraperService scraperService;

    public void register(String studentId, Long courseId, String token) {
        Course course = courseRepo.findById(courseId).orElseThrow();

        boolean hanuSuccess = scraperService.registerCourseOnPortal(course.getIdToHoc(), token);
        
        if (hanuSuccess) {
            course.setEnrolled(course.getEnrolled() + 1);
            courseRepo.save(course);

            RegistrationRecord record = new RegistrationRecord();
            record.setStudentId(studentId);
            record.setCourse(course);
            recordRepo.save(record);
        } else {
            throw new RuntimeException("Thất bại");
        }
    }

    public void drop(String studentId, Long courseId, String token) {
        RegistrationRecord record = recordRepo.findByStudentIdAndCourseId(studentId, courseId);
        if (record != null) {
            Course course = record.getCourse();
            boolean hanuSuccess = scraperService.dropCourseOnPortal(course.getIdToHoc(), token);
            
            if (hanuSuccess) {
                course.setEnrolled(course.getEnrolled() - 1);
                courseRepo.save(course);
                recordRepo.delete(record);
            }
        }
    }
}