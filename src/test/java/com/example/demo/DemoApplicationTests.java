package com.example.demo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class DemoApplicationTests {

    @Test
    void desktopScheduleServicePersistsData() throws Exception {
        Path tempDb = Files.createTempDirectory("scadule-desktop-test");
        DesktopScheduleService service = new DesktopScheduleService(tempDb.resolve("schedules").toString());

        service.initialize();
        service.saveSchedule("Team meeting", "2026-09-10");

        List<ScheduleItem> items = service.listSchedules();
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals("Team meeting", items.get(0).getTitle());
        assertEquals("2026-09-10", items.get(0).getDate());
    }
}
