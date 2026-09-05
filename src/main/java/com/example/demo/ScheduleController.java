package com.example.demo;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScheduleController {
    private final ScheduleRepository scheduleRepository;

    public ScheduleController(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

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

        // データベースから全てのスケジュールを取得
        List<DEMO> scheduleList = scheduleRepository.findAllByOrderByDate();
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
        // 新しいスケジュールを作成してデータベースに保存
        DEMO schedule = new DEMO(title, date);
        scheduleRepository.save(schedule);
        
        LocalDate targetDate = LocalDate.parse(date);
        return "redirect:/?year=" + targetDate.getYear() + "&month=" + targetDate.getMonthValue();
    }

    @GetMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable Long id) {
        // IDでスケジュールを取得して削除
        DEMO schedule = scheduleRepository.findById(id).orElse(null);
        if (schedule != null) {
            LocalDate targetDate = LocalDate.parse(schedule.getDate());
            scheduleRepository.deleteById(id);
            return "redirect:/?year=" + targetDate.getYear() + "&month=" + targetDate.getMonthValue();
        }
        return "redirect:/";
    }
}

