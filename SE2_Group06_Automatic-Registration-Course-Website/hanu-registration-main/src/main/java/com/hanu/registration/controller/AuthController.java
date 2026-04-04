package com.hanu.registration.controller;

import com.hanu.registration.service.HanuScraperService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private HanuScraperService scraperService;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String studentId, @RequestParam String password, HttpSession session, Model model) {
        String token = scraperService.loginAndGetToken(studentId, password);
        
        if (token != null) {
            session.setAttribute("studentId", studentId);
            session.setAttribute("jwtToken", token);
            return "redirect:/dashboard";
        }
        
        model.addAttribute("error", "Đăng nhập thất bại.");
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}