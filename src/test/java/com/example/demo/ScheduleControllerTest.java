package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ScheduleControllerTest {

    @BeforeEach
    void clearScheduleList() throws Exception {
        getScheduleList().clear();
    }

    @Test
    void indexAddsSchedulesToModelAndReturnsCalendarView() throws Exception {
        ScheduleController controller = new ScheduleController();
        Model model = new ConcurrentModel();

        String viewName = controller.index(model);

        assertEquals("calendar", viewName);
        assertNotNull(model.getAttribute("schedules"));
        assertEquals(0, getScheduleList().size());
    }

    @Test
    void addScheduleRedirectsToRootAndStoresEntry() throws Exception {
        ScheduleController controller = new ScheduleController();

        String viewName = controller.addSchedule("Meeting", "2026-07-22");

        assertEquals("redirect:/", viewName);
        assertEquals(1, getScheduleList().size());
    }

    @SuppressWarnings("unchecked")
    private static List<DEMO> getScheduleList() throws Exception {
        Field field = ScheduleController.class.getDeclaredField("scheduleList");
        field.setAccessible(true);
        return (List<DEMO>) field.get(null);
    }
}
