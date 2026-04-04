package com.hanu.registration.controller;

import com.hanu.registration.model.Course;
import com.hanu.registration.repository.CourseRepository;
import com.hanu.registration.repository.RegistrationRecordRepository;
import com.hanu.registration.service.HanuScraperService;
import com.hanu.registration.service.RegistrationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private CourseRepository courseRepo;
    @Autowired
    private RegistrationRecordRepository recordRepo;
    @Autowired
    private RegistrationService regService;
    @Autowired
    private HanuScraperService scraperService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String studentId = (String) session.getAttribute("studentId");
        if (studentId == null) return "redirect:/login";

        model.addAttribute("allCourses", courseRepo.findAll());
        model.addAttribute("myRecords", recordRepo.findByStudentId(studentId));
        return "dashboard";
    }

    @PostMapping("/sync-courses")
    public String syncCourses(HttpSession session, RedirectAttributes attrs) {
        String token = (String) session.getAttribute("jwtToken");
        if (token == null) return "redirect:/login";

        try {
            List<Course> liveCourses = scraperService.fetchAllCoursesFromPortal(token);
            courseRepo.deleteAll();
            courseRepo.saveAll(liveCourses);
            attrs.addFlashAttribute("msg", "Đã làm mới danh sách môn học!");
        } catch (Exception e) {
            attrs.addFlashAttribute("error", "Lỗi đồng bộ.");
        }
        return "redirect:/dashboard";
    }

    @PostMapping("/register-batch")
    public String registerBatchCourses(@RequestParam(value = "courseIds", required = false) List<Long> courseIds, 
                                       HttpSession session, RedirectAttributes attrs) {
        String studentId = (String) session.getAttribute("studentId");
        String token = (String) session.getAttribute("jwtToken");

        if (courseIds == null || courseIds.isEmpty()) {
            attrs.addFlashAttribute("error", "Vui lòng chọn ít nhất một môn học!");
            return "redirect:/dashboard";
        }

        int successCount = 0;
        int failCount = 0;

        for (Long courseId : courseIds) {
            try {
                regService.register(studentId, courseId, token);
                successCount++;
            } catch (Exception e) {
                failCount++;
            }
        }

        attrs.addFlashAttribute("msg", "Đăng ký xong! Thành công: " + successCount + " | Thất bại: " + failCount);
        return "redirect:/dashboard";
    }

    @PostMapping("/drop")
    public String dropCourse(@RequestParam Long courseId, HttpSession session) {
        String studentId = (String) session.getAttribute("studentId");
        String token = (String) session.getAttribute("jwtToken");
        regService.drop(studentId, courseId, token);
        return "redirect:/dashboard";
    }
}