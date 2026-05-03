import javax.swing.*;
import java.awt.*;

public class EmployeeLoginPage extends JFrame {

    // FIXED EMPLOYEE CREDENTIALS
    private static final String EMP_ID = "EMP001";
    private static final String EMP_PASS = "miemployee@123";

    public EmployeeLoginPage() {

        setTitle("Employee Login");
        setSize(1920, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Background Image (same)

        ImageIcon icon = new ImageIcon("C:\\Users\\User\\OneDrive\\Documents\\mi.png");
        Image img = icon.getImage().getScaledInstance(1400, 800, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(img));
        background.setBounds(160, 150, 1600, 800);
        background.setLayout(null);
        setContentPane(background);

        // BACK BUTTON

        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(1150, 720, 100, 30);
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setBackground(new Color(255, 255, 255));
        backBtn.setOpaque(true);
        backBtn.setBorderPainted(false);
        background.add(backBtn);

        backBtn.addActionListener(e -> {
            WelcomeUI page = new WelcomeUI();
            UIUtils.slideInFromRight(page);
            dispose();
        });

        // FORM

        int labelX = 390;
        int fieldX = 600;
        int y = 300;
        int gap = 70;

        JLabel idLabel = new JLabel("Employee ID:");
        idLabel.setBounds(labelX, y, 150, 40);
        idLabel.setFont(new Font("Arial", Font.BOLD, 18));
        background.add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(fieldX, y, 300, 40);
        background.add(idField);
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

        // SIGN IN BUTTON

        JButton signInBtn = new JButton("SIGN IN");
        signInBtn.setBounds(550, y, 150, 40);
        signInBtn.setFont(new Font("Arial", Font.BOLD, 18));
        signInBtn.setBackground(new Color(255, 255, 255));
        signInBtn.setOpaque(true);
        signInBtn.setBorderPainted(false);
        background.add(signInBtn);

        signInBtn.addActionListener(e -> {

            String enteredId = idField.getText().trim();
            String enteredPass = new String(passField.getPassword());

            if (enteredId.equals(EMP_ID) && enteredPass.equals(EMP_PASS)) {
//                JOptionPane.showMessageDialog(this, "Employee Login Successful!");
                EmployeeDashboard dash = new EmployeeDashboard();
                UIUtils.slideInFromRight(dash);
                dispose();


            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Employee ID or Password",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        setVisible(true);
    }
}