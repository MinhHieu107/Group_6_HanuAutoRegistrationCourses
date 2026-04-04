package com.hanu.registration.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanu.registration.model.Course;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HanuScraperService {
    
    private final String API_LOGIN_URL = "https://qldt.hanu.edu.vn/api/auth/login"; 
    private final String API_ALL_COURSES_URL = "https://qldt.hanu.edu.vn/api/dkmh/w-locdsnhomto";
    private final String API_REGISTER_URL = "https://qldt.hanu.edu.vn/api/dkmh/w-xulydkmhsinhvien"; 

    private final ObjectMapper objectMapper = new ObjectMapper(); 

    public String loginAndGetToken(String username, String password) {
        try {
            Connection.Response res = Jsoup.connect(API_LOGIN_URL)
                    .data("username", username)
                    .data("password", password)
                    .data("grant_type", "password")
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(30000)
                    .execute();
                    
            if (res.statusCode() == 200) {
                JsonNode jsonResponse = objectMapper.readTree(res.body());
                if (jsonResponse.has("access_token")) {
                    return jsonResponse.get("access_token").asText(); 
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null; 
    }

    public List<Course> fetchAllCoursesFromPortal(String jwtToken) {
        List<Course> courseList = new ArrayList<>();
        try {
            String jsonBody = "{\"is_CVHT\": false, \"additional\": {\"paging\": {\"limit\": 99999, \"page\": 1}, \"ordering\": [{\"name\": \"\", \"order_type\": \"\"}]}}";

            Connection.Response res = Jsoup.connect(API_ALL_COURSES_URL)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + jwtToken)
                    .requestBody(jsonBody)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(30000)
                    .execute();

            if (res.statusCode() == 200) {
                JsonNode coursesArray = objectMapper.readTree(res.body()).path("data").path("ds_nhom_to");
                if (coursesArray.isArray()) {
                    for (JsonNode node : coursesArray) {
                        Course course = new Course();
                        course.setIdToHoc(node.path("id_to_hoc").asText());
                        course.setCourseCode(node.path("ma_mon").asText());
                        course.setCourseName(node.path("ten_mon").asText());
                        course.setCredits(node.path("so_tc").asInt());
                        course.setCapacity(node.path("sl_cp").asInt());
                        course.setEnrolled(node.path("sl_dk").asInt());
                        course.setGroupName(node.path("nhom_to").asText());
                        course.setSubGroup(node.path("to").asText());

                        String rawTkb = node.path("tkb").asText();
                        String lecturer = "";
                        Matcher m = Pattern.compile("GV\\s+([^,<]+)").matcher(rawTkb);
                        if (m.find()) {
                            lecturer = m.group(1).trim();
                        }
                        course.setLecturer(lecturer);
                        
                        String cleanTkb = rawTkb.replace("<hr>", "<br><br>");
                        course.setScheduleTime(cleanTkb);

                        courseList.add(course);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return courseList;
    }

    public boolean registerCourseOnPortal(String idToHoc, String jwtToken) {
        try {
            String jsonBody = String.format("{\"filter\": {\"id_to_hoc\": \"%s\", \"is_checked\": true, \"sv_nganh\": 1}}", idToHoc);

            Connection.Response res = Jsoup.connect(API_REGISTER_URL)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + jwtToken) 
                    .requestBody(jsonBody)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(5000)
                    .execute();

            if (res.statusCode() == 200) {
                JsonNode dataNode = objectMapper.readTree(res.body()).get("data");
                if (dataNode != null && dataNode.has("is_thanh_cong")) {
                    return dataNode.get("is_thanh_cong").asBoolean();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean dropCourseOnPortal(String idToHoc, String jwtToken) {
        return true; 
    }
}