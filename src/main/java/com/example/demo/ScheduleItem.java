package com.example.demo;

public class ScheduleItem {
    private Long id;
    private String title;
    private String date;

    public ScheduleItem() {
    }

    public ScheduleItem(Long id, String title, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
