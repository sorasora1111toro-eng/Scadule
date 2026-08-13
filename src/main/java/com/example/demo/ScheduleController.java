package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ScheduleController {
    // スケジュールを保存する簡易的なリスト（本来はデータベースを使います）
    private static final List<DEMO> scheduleList = new ArrayList<>();

    @GetMapping("/")
    public String index(@RequestParam(required = false) Integer year,
                        @RequestParam(required = false) Integer month,
                        Model model) {
        LocalDate baseDate = LocalDate.now();
        if (year != null && month != null) {
            baseDate = LocalDate.of(year, month, 1);
        }

        YearMonth yearMonth = YearMonth.from(baseDate);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        int offset = firstDayOfMonth.getDayOfWeek().getValue() % 7;
        LocalDate calendarStart = firstDayOfMonth.minusDays(offset);

        List<List<LocalDate>> calendarWeeks = new ArrayList<>();
        List<LocalDate> calendarDates = new ArrayList<>();
        for (int i = 0; i < 42; i++) {
            LocalDate date = calendarStart.plusDays(i);
            calendarDates.add(date);
            if (calendarDates.size() == 7) {
                calendarWeeks.add(new ArrayList<>(calendarDates));
                calendarDates.clear();
            }
        }

        Map<String, List<DEMO>> scheduleMap = new HashMap<>();
        for (DEMO schedule : scheduleList) {
            scheduleMap.computeIfAbsent(schedule.getDate(), key -> new ArrayList<>()).add(schedule);
        }

        model.addAttribute("calendarWeeks", calendarWeeks);
        model.addAttribute("scheduleMap", scheduleMap);
        model.addAttribute("currentYear", yearMonth.getYear());
        model.addAttribute("currentMonth", yearMonth.getMonthValue());
        model.addAttribute("prevMonth", yearMonth.minusMonths(1));
        model.addAttribute("nextMonth", yearMonth.plusMonths(1));
        return "calendar";
    }

    @PostMapping("/add")
    public String addSchedule(@RequestParam String title, @RequestParam String date) {
        scheduleList.add(new DEMO(title, date));
        LocalDate targetDate = LocalDate.parse(date);
        return "redirect:/?year=" + targetDate.getYear() + "&month=" + targetDate.getMonthValue();
    }
}

