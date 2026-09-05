package com.example.demo;

import javax.swing.SwingUtilities;

public class DemoApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DesktopScheduleApp app = new DesktopScheduleApp();
            app.setVisible(true);
        });
    }
}
