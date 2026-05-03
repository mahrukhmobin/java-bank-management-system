import javax.swing.*;
import java.awt.*;


public class EmployeeDashboard extends JFrame {
    private JPanel dropdownPanel;

    public EmployeeDashboard() {

        setTitle("Employee Dashboard");
        setSize(1920, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Background Image

        ImageIcon icon = new ImageIcon("C:\\Users\\User\\OneDrive\\Documents\\mi (8).png");
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

        // CENTER BUTTONS (SEPARATE POSITIONS)

        int btnWidth = 250;
        int btnHeight = 60;

        // Bank Accounts
        JButton bankAccountsBtn = new JButton("Bank Accounts");
        bankAccountsBtn.setBounds(500, 250, btnWidth, btnHeight);
        bankAccountsBtn.setBackground(Color.WHITE);
        bankAccountsBtn.setBorderPainted(false);
        bankAccountsBtn.setFont(new Font("Arial", Font.BOLD, 16));
        background.add(bankAccountsBtn);


        // Remove Client
        JButton removeClientBtn = new JButton("Remove Client");
        removeClientBtn.setBounds(500, 400, btnWidth, btnHeight);
        removeClientBtn.setBackground(Color.WHITE);
        removeClientBtn.setBorderPainted(false);
        removeClientBtn.setFont(new Font("Arial", Font.BOLD, 16));
        background.add(removeClientBtn);

        // Assets
        JButton assetsBtn = new JButton("Assets");
        assetsBtn.setBounds(500, 550, btnWidth, btnHeight);
        assetsBtn.setBackground(Color.WHITE);
        assetsBtn.setBorderPainted(false);
        assetsBtn.setFont(new Font("Arial", Font.BOLD, 16));
        background.add(assetsBtn);

        // BANK ACCOUNTS ACTION

        bankAccountsBtn.addActionListener(e -> {
            new BankAccountsDialog(this);
        });

        // REMOVE CLIENT ACTION

        removeClientBtn.addActionListener(e -> {
            new RemoveClientDialog(this);
        });

        // ASSETS ACTION

        assetsBtn.addActionListener(e -> {
            new AssetsDialog(this);
        });

        setVisible(true);
    }

}