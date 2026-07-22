package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ScheduleController {
    private static final List<DEMO> scheduleList = new ArrayList<>();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("schedules", scheduleList);
        return "calendar";
    }

    @PostMapping("/add")
    public String addSchedule(@RequestParam String title, @RequestParam String date) {
        scheduleList.add(new DEMO(title, date));
        return "redirect:/";
    }
}
