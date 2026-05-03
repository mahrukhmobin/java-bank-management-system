import javax.swing.*;
import java.awt.*;
import java.io.*;

public class AssetsDialog extends JDialog {

    public AssetsDialog(JFrame parent) {
        super(parent, "Bank Assets", true);
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // TOP PANEL - Total Balance

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 100, 80));
        topPanel.setPreferredSize(new Dimension(600, 80));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JLabel totalLabel = new JLabel("TOTAL BANK BALANCE: Rs. " + getTotalBalance());
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalLabel.setForeground(Color.WHITE);
        topPanel.add(totalLabel);

        add(topPanel, BorderLayout.NORTH);

        // CENTER PANEL - Account List

        JTextArea accountsArea = new JTextArea();
        accountsArea.setEditable(false);
        accountsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        accountsArea.setMargin(new Insets(10, 10, 10, 10));

        String accountsData = getAccountsDetails();
        accountsArea.setText(accountsData);

        JScrollPane scrollPane = new JScrollPane(accountsArea);
        add(scrollPane, BorderLayout.CENTER);

        // BOTTOM PANEL - Close Button

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(600, 60));

        JButton closeBtn = new JButton("CLOSE");
        closeBtn.setFont(new Font("Arial", Font.BOLD, 14));
        closeBtn.setPreferredSize(new Dimension(120, 35));
        closeBtn.setBackground(Color.WHITE);
        closeBtn.addActionListener(e -> dispose());

        bottomPanel.add(closeBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Get Total Balance from all accounts

    private double getTotalBalance() {
        double total = 0.0;

        try {
            File file = new File("accounts.txt");
            if (!file.exists()) {
                return 0.0;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // data[6] = balance
                total += Double.parseDouble(data[6]);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    // Get All Accounts Details

    private String getAccountsDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s %-25s %-15s%n", "ACCOUNT NO", "ACCOUNT HOLDER", "BALANCE"));
        sb.append("=".repeat(60)).append("\n");

        try {
            File file = new File("accounts.txt");
            if (!file.exists()) {
                return "No accounts found.";
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // data[0] = account number
                // data[1] = name
                // data[6] = balance

                String accNo = data[0];
                String name = data[1];
                double balance = Double.parseDouble(data[6]);

                sb.append(String.format("%-15s %-25s Rs. %-12.2f%n",
                        accNo, name, balance));
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading account details.";
        }

        return sb.toString();
    }
}