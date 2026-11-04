import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            StopwatchFrame frame = new StopwatchFrame();
            frame.setVisible(true);
        });

    }
}