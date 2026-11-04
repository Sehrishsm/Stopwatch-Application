import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class StopwatchFrame extends JFrame {

    private Stopwatch stopwatch;
    private JLabel timeLabel;
    private JButton startButton;
    private JButton lapButton;
    private JButton resetButton;
    private DefaultListModel<String> lapListModel;
    private int lapCount = 0;

    private Timer timer;

    public StopwatchFrame() {
        stopwatch = new Stopwatch();

        setTitle("Stopwatch");
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createUI();

        timer = new Timer(10, e -> updateTime());
    }

    private void createUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(20, 25, 40));
        mainPanel.setLayout(new BorderLayout(15, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 25, 25, 25));

        JLabel titleLabel = new JLabel("STOPWATCH", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        timeLabel = new JLabel("00:00:00.000", SwingConstants.CENTER);
        timeLabel.setForeground(new Color(0, 220, 180));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 34));

        JPanel displayPanel = new JPanel(new BorderLayout(10, 25));
        displayPanel.setBackground(new Color(20, 25, 40));
        displayPanel.add(titleLabel, BorderLayout.NORTH);
        displayPanel.add(timeLabel, BorderLayout.CENTER);

        startButton = createButton("Start", new Color(0, 170, 130));
        lapButton = createButton("Lap", new Color(65, 105, 180));
        resetButton = createButton("Reset", new Color(190, 65, 65));

        lapButton.setEnabled(false);
        resetButton.setEnabled(false);

        startButton.addActionListener(e -> startOrPause());
        lapButton.addActionListener(e -> addLap());
        resetButton.addActionListener(e -> resetStopwatch());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBackground(new Color(20, 25, 40));
        buttonPanel.add(startButton);
        buttonPanel.add(lapButton);
        buttonPanel.add(resetButton);

        lapListModel = new DefaultListModel<>();
        JList<String> lapList = new JList<>(lapListModel);
        lapList.setBackground(new Color(32, 39, 58));
        lapList.setForeground(Color.WHITE);
        lapList.setFont(new Font("Arial", Font.PLAIN, 15));

        JScrollPane scrollPane = new JScrollPane(lapList);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Lap History",
                0,
                0,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE));
        scrollPane.getViewport().setBackground(new Color(32, 39, 58));

        mainPanel.add(displayPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void startOrPause() {
        if (!stopwatch.isRunning()) {
            stopwatch.start();
            startButton.setText("Pause");
            lapButton.setEnabled(true);
            resetButton.setEnabled(true);
            timer.start();
        } else {
            stopwatch.pause();
            startButton.setText("Resume");
            lapButton.setEnabled(false);
        }
    }

    private void addLap() {
        lapCount++;
        String lapTime = formatTime(stopwatch.getElapsedTime());

        lapListModel.addElement("Lap " + lapCount + "     " + lapTime);
    }

    private void resetStopwatch() {
        stopwatch.reset();
        timer.stop();

        timeLabel.setText("00:00:00.000");
        startButton.setText("Start");
        lapButton.setEnabled(false);
        resetButton.setEnabled(false);

        lapCount = 0;
        lapListModel.clear();
    }

    private void updateTime() {
        timeLabel.setText(formatTime(stopwatch.getElapsedTime()));
    }

    private String formatTime(long elapsedNanos) {
        long milliseconds = elapsedNanos / 1_000_000;

        long hours = milliseconds / 3_600_000;
        long minutes = (milliseconds / 60_000) % 60;
        long seconds = (milliseconds / 1_000) % 60;
        long millis = milliseconds % 1_000;

        return String.format(
                "%02d:%02d:%02d.%03d",
                hours, minutes, seconds, millis);
    }
}