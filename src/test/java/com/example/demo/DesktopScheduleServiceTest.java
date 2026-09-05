package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

class DesktopScheduleServiceTest {

    @Test
    void saveAndListSchedules() throws Exception {
        Path dbDir = Files.createTempDirectory("desktop-schedule-test");
        DesktopScheduleService service = new DesktopScheduleService(dbDir.resolve("desktop-schedule.db").toString());

        service.initialize();
        service.saveSchedule("Meeting", "2026-09-10");

        List<ScheduleItem> items = service.listSchedules();
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals("Meeting", items.get(0).getTitle());
        assertEquals("2026-09-10", items.get(0).getDate());
    }
}
