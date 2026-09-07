package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DEMOTest {

    @Test
    void constructorStoresFields() {
        DEMO demo = new DEMO("Team Sync", "2026-07-22");

        assertEquals("Team Sync", demo.getTitle());
        assertEquals("2026-07-22", demo.getDate());
    }

    @Test
    void settersUpdateFields() {
        DEMO demo = new DEMO("Old", "2026-07-01");

        demo.setTitle("New");
        demo.setDate("2026-07-30");

        assertEquals("New", demo.getTitle());
        assertEquals("2026-07-30", demo.getDate());
    }
}
