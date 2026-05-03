import javax.swing.*;
import java.awt.*;

public class WelcomeUI extends JFrame {

    public WelcomeUI() {
        setTitle("mi Bank Limited");
        setSize(1920, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Load Background Image

        ImageIcon originalIcon = new ImageIcon("C:\\Users\\User\\OneDrive\\Documents\\mi (4).png");
        Image img = originalIcon.getImage();

        int frameWidth = 1920;
        int frameHeight = 1200;

        double imgWidth = img.getWidth(null);
        double imgHeight = img.getHeight(null);

        double widthRatio = frameWidth / imgWidth;
        double heightRatio = frameHeight / imgHeight;
        double ratio = Math.min(widthRatio, heightRatio) * 0.8;

        int newWidth = (int) (imgWidth * ratio);
        int newHeight = (int) (imgHeight * ratio);

        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel background = new JLabel(scaledIcon);
        background.setBounds((frameWidth - newWidth) / 2, (frameHeight - newHeight) / 2, newWidth, newHeight);
        background.setLayout(null);
        setContentPane(background);

        // Button Settings (FULL CONTROL)

        int clientX = 950;
        int clientY = 300;
        int employeeX = 950;
        int employeeY = 400;
        int btnWidth = 200;
        int btnHeight = 55;

        // CLIENT Button
        JButton clientBtn = new JButton("CLIENT");
        clientBtn.setBounds(clientX, clientY, btnWidth, btnHeight);
        clientBtn.setFont(new Font("Arial", Font.BOLD, 18));
        clientBtn.setBackground(new Color(255, 255, 255));
        clientBtn.setOpaque(true);
        clientBtn.setBorderPainted(false);
        background.add(clientBtn);

        //CLIENT BUTTON ACTION LISTENER
        clientBtn.addActionListener(e -> {
            ClientPage page = new ClientPage();
            UIUtils.slideInFromRight(page);
            this.dispose();
        });


        // EMPLOYEE Button
        JButton employeeBtn = new JButton("EMPLOYEE");
        employeeBtn.setBounds(employeeX, employeeY, btnWidth, btnHeight);
        employeeBtn.setFont(new Font("Arial", Font.BOLD, 18));
        employeeBtn.setBackground(new Color(255, 255, 255));
        employeeBtn.setOpaque(true);
        employeeBtn.setBorderPainted(false);
        background.add(employeeBtn);

        //CLIENT BUTTON ACTION LISTENER
        employeeBtn.addActionListener(e -> {
            EmployeeLoginPage page = new EmployeeLoginPage();
            UIUtils.slideInFromRight(page);
            this.dispose();
        });


        setVisible(true);
    }




    public static void main(String[] args) {
        new WelcomeUI();
    }
}