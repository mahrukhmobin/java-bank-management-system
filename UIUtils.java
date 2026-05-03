import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;

public class UIUtils {

    public static void slideInFromRight(JFrame frame) {
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        // Start off-screen
        frame.setLocation(screenWidth, 0);
        frame.setVisible(true);

        Timer timer = new Timer(5, null);
        timer.addActionListener(e -> {
            Point p = frame.getLocation();
            if (p.x <= 0) {
                frame.setLocation(0, 0);
                ((Timer) e.getSource()).stop();
            } else {
                frame.setLocation(p.x - 40, p.y);
            }
        });
        timer.start();
    }
}