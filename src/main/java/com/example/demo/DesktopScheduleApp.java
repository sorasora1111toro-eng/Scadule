package com.example.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class DesktopScheduleApp extends JFrame {
    private final DesktopScheduleService service;
    private final JLabel monthLabel = new JLabel();
    private final JPanel calendarPanel = new JPanel(new GridLayout(0, 7, 4, 4));
    private YearMonth displayedMonth = YearMonth.now();

    public DesktopScheduleApp() {
        String appDir = System.getProperty("user.home") + File.separator + "ScaduleDesktop";
        File folder = new File(appDir);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String dbPath = appDir + File.separator + "schedules";
        this.service = new DesktopScheduleService(dbPath);
        this.service.initialize();

        setTitle("Scadule Desktop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout(12, 12));
        main.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel header = new JLabel("Scadule Desktop");
        header.setFont(new Font("SansSerif", Font.BOLD, 24));

        JPanel top = new JPanel(new BorderLayout(12, 0));
        top.add(header, BorderLayout.WEST);
        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton previousButton = new JButton("<");
        JButton nextButton = new JButton(">");
        previousButton.addActionListener(e -> { displayedMonth = displayedMonth.minusMonths(1); renderCalendar(); });
        nextButton.addActionListener(e -> { displayedMonth = displayedMonth.plusMonths(1); renderCalendar(); });
        navigation.add(previousButton);
        navigation.add(monthLabel);
        navigation.add(nextButton);
        top.add(navigation, BorderLayout.EAST);

        main.add(top, BorderLayout.NORTH);
        main.add(calendarPanel, BorderLayout.CENTER);
        setContentPane(main);
        setLocationRelativeTo(null);
        renderCalendar();
    }

    private void renderCalendar() {
        calendarPanel.removeAll();
        monthLabel.setText(displayedMonth.getYear() + " / " + displayedMonth.getMonthValue());
        for (String day : new String[] {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}) {
            calendarPanel.add(new JLabel(day, JLabel.CENTER));
        }

        Map<String, List<ScheduleItem>> schedulesByDate = new HashMap<>();
        List<ScheduleItem> schedules = service.listSchedules();
        for (ScheduleItem item : schedules) {
            schedulesByDate.computeIfAbsent(item.getDate(), key -> new ArrayList<>()).add(item);
        }

        LocalDate firstDay = displayedMonth.atDay(1);
        LocalDate calendarStart = firstDay.minusDays(firstDay.getDayOfWeek().getValue() % 7);
        for (int index = 0; index < 42; index++) {
            LocalDate date = calendarStart.plusDays(index);
            JPanel dayPanel = new JPanel(new BorderLayout());
            dayPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            dayPanel.setBackground(date.equals(LocalDate.now()) ? new Color(225, 240, 255) : Color.WHITE);
            JButton dateButton = new JButton(String.valueOf(date.getDayOfMonth()));
            dateButton.setHorizontalAlignment(JButton.LEFT);
            dateButton.setBorderPainted(false);
            dateButton.setContentAreaFilled(false);
            dateButton.setForeground(date.getMonth() == displayedMonth.getMonth() ? Color.BLACK : Color.GRAY);
            dateButton.addActionListener(e -> addScheduleForDate(date));
            dayPanel.add(dateButton, BorderLayout.NORTH);

            JPanel events = new JPanel();
            events.setLayout(new javax.swing.BoxLayout(events, javax.swing.BoxLayout.Y_AXIS));
            events.setOpaque(false);
            for (ScheduleItem item : schedulesByDate.getOrDefault(date.toString(), List.of())) {
                events.add(new JLabel("• " + item.getTitle()));
            }
            dayPanel.add(events, BorderLayout.CENTER);
            calendarPanel.add(dayPanel);
        }
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void addScheduleForDate(LocalDate date) {
        javax.swing.JTextField titleField = new javax.swing.JTextField();
        int result = JOptionPane.showConfirmDialog(this, titleField, "Add schedule for " + date,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !titleField.getText().trim().isEmpty()) {
            service.saveSchedule(titleField.getText().trim(), date.toString());
            renderCalendar();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DesktopScheduleApp app = new DesktopScheduleApp();
            app.setVisible(true);
        });
    }
}
