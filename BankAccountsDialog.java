import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class BankAccountsDialog extends JDialog {

    private JComboBox<String> accountDropdown;
    private JTextArea detailsArea;
    private ArrayList<String[]> allAccounts;

    public BankAccountsDialog(JFrame parent) {
        super(parent, "Bank Accounts", true);
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        allAccounts = new ArrayList<>();
        loadAccounts();

        // TOP PANEL - Dropdown

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 100, 80));
        topPanel.setPreferredSize(new Dimension(600, 80));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 25));

        JLabel selectLabel = new JLabel("Select Account: ");
        selectLabel.setFont(new Font("Arial", Font.BOLD, 16));
        selectLabel.setForeground(Color.WHITE);

        accountDropdown = new JComboBox<>();
        accountDropdown.setPreferredSize(new Dimension(250, 35));
        accountDropdown.setFont(new Font("Arial", Font.PLAIN, 14));

        // Populate dropdown with account numbers
        accountDropdown.addItem("-- Select Account --");
        for (String[] account : allAccounts) {
            accountDropdown.addItem(account[0] + " - " + account[1]);
        }

        topPanel.add(selectLabel);
        topPanel.add(accountDropdown);

        add(topPanel, BorderLayout.NORTH);

        // CENTER PANEL - Account Details

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        detailsArea.setMargin(new Insets(20, 20, 20, 20));
        detailsArea.setText("\n\n          Select an account to view details");

        JScrollPane scrollPane = new JScrollPane(detailsArea);
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

        // Dropdown Action Listener

        accountDropdown.addActionListener(e -> {
            int selectedIndex = accountDropdown.getSelectedIndex();
            if (selectedIndex > 0) { // Skip "-- Select Account --"
                displayAccountDetails(allAccounts.get(selectedIndex - 1));
            } else {
                detailsArea.setText("\n\n          Select an account to view details");
            }
        });

        setVisible(true);
    }

    // Load all accounts from file

    private void loadAccounts() {
        try {
            File file = new File("accounts.txt");
            if (!file.exists()) {
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                allAccounts.add(data);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Display account details (except password)

    private void displayAccountDetails(String[] account) {
        // Format: accountNo,name,phone,cnic,username,password,balance,email
        String accountNo = account[0];
        String name = account[1];
        String phone = account[2];
        String cnic = account[3];
        String username = account[4];
        // password = account[5] - NOT DISPLAYED
        String balance = account[6];
        String email = account[7];

        StringBuilder sb = new StringBuilder();
        sb.append("  ╔════════════════════════════════════════════════════╗\n");
        sb.append("  ║          ACCOUNT INFORMATION               ║\n");
        sb.append("  ╚════════════════════════════════════════════════════╝\n\n");

        sb.append("  Account Number    : ").append(accountNo).append("\n\n");
        sb.append("  Account Holder    : ").append(name).append("\n\n");
        sb.append("  Phone Number      : ").append(phone).append("\n\n");
        sb.append("  CNIC              : ").append(cnic).append("\n\n");
        sb.append("  Email             : ").append(email).append("\n\n");
        sb.append("  Username          : ").append(username).append("\n\n");
        sb.append("  Current Balance   : Rs. ").append(balance).append("\n\n");

        sb.append("  ════════════════════════════════════════════════════\n");

        detailsArea.setText(sb.toString());
    }
}