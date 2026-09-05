package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DesktopScheduleService {
    private final String dbUrl;

    public DesktopScheduleService(String dbFilePath) {
        this.dbUrl = "jdbc:h2:file:" + dbFilePath + ";AUTO_SERVER=TRUE";
    }

    public void initialize() {
        try (Connection connection = DriverManager.getConnection(dbUrl, "sa", "")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS schedules (" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255) NOT NULL, " +
                    "date VARCHAR(10) NOT NULL)"
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize desktop schedule database", e);
        }
    }

    public void saveSchedule(String title, String date) {
        try (Connection connection = DriverManager.getConnection(dbUrl, "sa", "")) {
            String sql = "INSERT INTO schedules (title, date) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, title);
                statement.setString(2, date);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save schedule", e);
        }
    }

    public List<ScheduleItem> listSchedules() {
        List<ScheduleItem> result = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbUrl, "sa", "")) {
            String sql = "SELECT id, title, date FROM schedules ORDER BY date, id";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(new ScheduleItem(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("date")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load schedules", e);
        }
        return result;
    }
}
