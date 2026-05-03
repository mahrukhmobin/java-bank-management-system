import javax.swing.*;
import java.awt.*;
import java.io.*;

public class SignInPage extends JFrame {

    public SignInPage() {
        setTitle("mi Bank System - Sign In");
        setSize(1920, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Background
        ImageIcon icon = new ImageIcon("C:\\Users\\User\\OneDrive\\Documents\\mi.png");
        Image img = icon.getImage().getScaledInstance(1400, 800, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(img));
        background.setBounds(160, 150, 1600, 800);
        background.setLayout(null);
        setContentPane(background);

        // Labels & Fields
        int labelX = 390, fieldX = 600, y = 300, gap = 70;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(labelX, y, 150, 40);
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        background.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(fieldX, y, 300, 40);
        background.add(userField);
        y += gap;

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(labelX, y, 150, 40);
        passLabel.setFont(new Font("Arial", Font.BOLD, 18));
        background.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(fieldX, y, 240, 40);
        background.add(passField);
        y += gap;


        JToggleButton eye = new JToggleButton("👁");
        eye.setBounds(848,370,50,40);
        eye.setBackground(Color.WHITE);
        background.add(eye);

        eye.addActionListener(e -> {
            if (eye.isSelected())
                passField.setEchoChar((char) 0);
            else
                passField.setEchoChar('●');
        });


        // BACK BUTTON
        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(1150, 720, 100, 30);
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setBackground(new Color(255, 255, 255));
        backBtn.setOpaque(true);
        backBtn.setBorderPainted(false);
        background.add(backBtn);

// ActionListener
        backBtn.addActionListener(e -> {
            ClientPage clientPage = new ClientPage();
            UIUtils.slideInFromRight(clientPage);
            this.dispose();
        });


        // Sign In Button
        JButton signInBtn = new JButton("SIGN IN");
        signInBtn.setBounds(550, y, 150, 40);
        signInBtn.setFont(new Font("Arial", Font.BOLD, 18));
        signInBtn.setBackground(new Color(255, 255, 255));
        signInBtn.setOpaque(true);
        signInBtn.setBorderPainted(false);
        background.add(signInBtn);

        signInBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            String[] accountData = validateLogin(username, password);
            if (accountData != null) {
                // Open Client Dashboard passing account data
                ClientDashboard dashboard = new ClientDashboard(accountData);
                dashboard.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    // Validate username & password from accounts.txt
    private String[] validateLogin(String username, String password) {
        try {
            File file = new File("accounts.txt");
            if (!file.exists()) return null;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                // Format: name,phone,cnic,username,password,balance,accountNumber
                String[] data = line.split(",");
                if (data[4].equals(username) && data[5].equals(password)) {
                    br.close();
                    return data;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        new SignInPage();
    }
}