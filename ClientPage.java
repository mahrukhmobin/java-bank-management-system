import javax.swing.*;
import java.awt.*;


public class ClientPage extends JFrame {

    public ClientPage() {
        setTitle("UET Bank System - Client");
        setSize(1920, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Background Image (same as WelcomeUI)

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

        // Button Positions

        int btnWidth = 250;
        int btnHeight = 60;

        int signInX = 950;
        int signInY = 300;

        int newAccountX = 950;
        int newAccountY = 400;

        // SIGN IN Button
        JButton signInBtn = new JButton("SIGN IN");
        signInBtn.setBounds(signInX, signInY, btnWidth, btnHeight);
        signInBtn.setFont(new Font("Arial", Font.BOLD, 18));
        signInBtn.setBackground(new Color(255, 255, 255));
        signInBtn.setOpaque(true);
        signInBtn.setBorderPainted(false);
        background.add(signInBtn);

        signInBtn.addActionListener(e -> {
            SignInPage signInPage = new SignInPage();
            UIUtils.slideInFromRight(signInPage);
            this.dispose();
        });


        // OPEN NEW ACCOUNT Button
        JButton newAccountBtn = new JButton("OPEN NEW ACCOUNT");
        newAccountBtn.setBounds(newAccountX, newAccountY, btnWidth, btnHeight);
        newAccountBtn.setFont(new Font("Arial", Font.BOLD, 18));
        newAccountBtn.setBackground(new Color(255, 255, 255));
        newAccountBtn.setOpaque(true);
        newAccountBtn.setBorderPainted(false);
        background.add(newAccountBtn);

        newAccountBtn.addActionListener(e -> {
//            new OpenAccountPage();
//            dispose();
            OpenAccountPage page = new OpenAccountPage();
            UIUtils.slideInFromRight(page);
            dispose();

        });

        // Back Button
        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(1150, 720, 100, 30);
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setBackground(new Color(255, 255, 255));
        backBtn.setOpaque(true);
        backBtn.setBorderPainted(false);
        background.add(backBtn);

// ActionListener for Back button
        backBtn.addActionListener(e -> {
            WelcomeUI page = new WelcomeUI();
            UIUtils.slideInFromRight(page); // slide effect
            this.dispose();



        });



        setVisible(true);
    }

}