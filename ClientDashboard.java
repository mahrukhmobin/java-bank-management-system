import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientDashboard extends JFrame {

    String accountNo, name, phone, cnic, email, password;
    double balance;

    private JLabel balanceLabel;
    private boolean balanceVisible = false;

    public ClientDashboard(String[] data) {

        accountNo = data[0];
        name = data[1];
        phone = data[2];
        cnic = data[3];
        password = data[5];
        balance = Double.parseDouble(data[6]);
        email = data[7];

        setTitle("Client Dashboard");
        setSize(1920, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        ImageIcon icon = new ImageIcon("C:\\Users\\User\\OneDrive\\Documents\\mi (3).png");
        Image img = icon.getImage().getScaledInstance(1400, 800, Image.SCALE_SMOOTH);
        JLabel bg = new JLabel(new ImageIcon(img));
        bg.setBounds(160, 150, 900, 400);
        bg.setLayout(null);
        setContentPane(bg);

        JButton backBtn = new JButton("BACK");
        backBtn.setBounds(1150, 720, 100, 30);
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setBackground(Color.WHITE);
        backBtn.setBorderPainted(false);
        bg.add(backBtn);

        backBtn.addActionListener(e -> {
            ClientPage cp = new ClientPage();
            UIUtils.slideInFromRight(cp);
            dispose();
        });

        JButton profileBtn = new JButton("Profile");
        profileBtn.setBounds(30, 170, 90, 30);
        profileBtn.setFont(new Font("Arial", Font.BOLD, 16));
        profileBtn.setBackground(Color.WHITE);
        profileBtn.setBorderPainted(false);
        bg.add(profileBtn);

        profileBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Name: " + name + "\n" +
                        "Account#: " + accountNo + "\n" +
                        "Phone: " + phone + "\n" +
                        "Email: " + email + "\n" +
                        "CNIC: " + cnic,
                "Profile Information", JOptionPane.INFORMATION_MESSAGE));

        JButton balanceBtn = new JButton("Show Balance");
        balanceBtn.setBounds(30, 220, 180, 30);
        balanceBtn.setFont(new Font("Arial", Font.BOLD, 16));
        balanceBtn.setBackground(Color.WHITE);
        balanceBtn.setBorderPainted(false);
        bg.add(balanceBtn);

        balanceLabel = new JLabel("");
        balanceLabel.setBounds(30, 260, 350, 30);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bg.add(balanceLabel);

        balanceBtn.addActionListener(e -> {
            if (!balanceVisible) {
                balanceLabel.setText("Current Balance: Rs. " + balance);
                balanceBtn.setText("Hide Balance");
            } else {
                balanceLabel.setText("");
                balanceBtn.setText("Show Balance");
            }
            balanceVisible = !balanceVisible;
        });

        JButton deposit = new JButton("Deposit");
        JButton withdraw = new JButton("Withdraw");
        JButton transfer = new JButton("Transfer");
        JButton sao = new JButton("SAO");

        deposit.setBounds(185, 450, 110, 30);
        withdraw.setBounds(450, 450, 110, 30);
        transfer.setBounds(680, 450, 110, 30);
        sao.setBounds(940, 450, 110, 30);

        deposit.setBackground(Color.WHITE);
        withdraw.setBackground(Color.WHITE);
        transfer.setBackground(Color.WHITE);
        sao.setBackground(Color.WHITE);

        deposit.setBorderPainted(false);
        withdraw.setBorderPainted(false);
        transfer.setBorderPainted(false);
        sao.setBorderPainted(false);

        deposit.setFont(new Font("Arial", Font.BOLD, 16));
        withdraw.setFont(new Font("Arial", Font.BOLD, 16));
        transfer.setFont(new Font("Arial", Font.BOLD, 16));
        sao.setFont(new Font("Arial", Font.BOLD, 16));

        bg.add(deposit);
        bg.add(withdraw);
        bg.add(transfer);
        bg.add(sao);

        deposit.addActionListener(e -> openTransactionDialog("Deposit"));
        withdraw.addActionListener(e -> openTransactionDialog("Withdraw"));
        transfer.addActionListener(e -> transfer());
        sao.addActionListener(e -> sao());

        setVisible(true);
    }

    // ================= DEPOSIT / WITHDRAW =================
    private void openTransactionDialog(String type) {

        JDialog d = new JDialog(this, type, true);
        d.setSize(400, 300);
        d.setLayout(new BorderLayout());
        d.setLocationRelativeTo(this);

// TOP PANEL - Green Header

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 100, 80));
        topPanel.setPreferredSize(new Dimension(400, 60));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        JLabel titleLabel = new JLabel(type.toUpperCase() + " AMOUNT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        topPanel.add(titleLabel);
        d.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(null);
        d.add(centerPanel, BorderLayout.CENTER);


        JLabel pl = new JLabel("Password:");
        JLabel al = new JLabel("Amount:");

        pl.setBounds(30, 50, 100, 30);
        al.setBounds(30, 90, 100, 30);

        JPasswordField p = new JPasswordField();
        char echo = p.getEchoChar();
        JTextField a = new JTextField();

        p.setBounds(140, 50, 150, 30);
        a.setBounds(140, 90, 150, 30);

        JToggleButton eye = new JToggleButton("👁");
        eye.setBounds(300, 50, 50, 30);
        eye.setBackground(Color.WHITE);
        eye.addActionListener(e -> p.setEchoChar(eye.isSelected() ? (char) 0 : echo));

        JButton ok = new JButton("Confirm");
        ok.setBackground(new Color(0,180,0));
        ok.setForeground(Color.WHITE);
        ok.setBorderPainted(false);
        ok.setFocusPainted(false);

        JButton no = new JButton("Cancel");
        no.setBackground(new Color(220, 53, 69));
        no.setForeground(Color.WHITE);
        no.setBorderPainted(false);
        no.setFocusPainted(false);

        ok.setBounds(80, 160, 100, 30);
        no.setBounds(200, 160, 100, 30);


        centerPanel.add(pl);
        centerPanel.add(al);
        centerPanel.add(p);
        centerPanel.add(a);
        centerPanel.add(eye);
        centerPanel.add(ok);
        centerPanel.add(no);


        no.addActionListener(e -> d.dispose());

        ok.addActionListener(e -> {

            if (!new String(p.getPassword()).equals(password)) {
                JOptionPane.showMessageDialog(d, "Wrong Password");
                return;
            }

            double amt;
            try { amt = Double.parseDouble(a.getText()); }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(d, "Invalid Amount");
                return;
            }

            if (type.equals("Withdraw") && amt > balance) {
                JOptionPane.showMessageDialog(d, "Insufficient Balance");
                return;
            }

            balance += type.equals("Deposit") ? amt : -amt;

            updateAccountFile();
            log(type.toUpperCase(), amt, "Self");
            sendTransactionEmail(type, amt);

            if (balanceVisible)
                balanceLabel.setText("Current Balance: Rs. " + balance);

            JOptionPane.showMessageDialog(d, type + " Successful");
            d.dispose();
        });

        d.setVisible(true);
    }

    // ================= TRANSFER =================
    private void transfer() {

        JDialog d = new JDialog(this, "Transfer", true);
        d.setSize(420, 330);
        d.setLayout(new BorderLayout());
        d.setLocationRelativeTo(this);

// TOP PANEL - Green Header

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 100, 80));
        topPanel.setPreferredSize(new Dimension(420, 60));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));

        JLabel titleLabel = new JLabel("TRANSFER FUNDS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        topPanel.add(titleLabel);
        d.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(null);
        d.add(centerPanel, BorderLayout.CENTER);



        JLabel toLbl = new JLabel("To Account:");
        JLabel passLbl = new JLabel("Password:");
        JLabel amtLbl = new JLabel("Amount:");

        toLbl.setBounds(30, 50, 120, 30);
        passLbl.setBounds(30, 90, 120, 30);
        amtLbl.setBounds(30, 130, 120, 30);

        JTextField to = new JTextField();
        JPasswordField p = new JPasswordField();
        char echo = p.getEchoChar();
        JTextField a = new JTextField();

        to.setBounds(160, 50, 200, 30);
        p.setBounds(160, 90, 160, 30);
        a.setBounds(160, 130, 200, 30);

        JToggleButton eye = new JToggleButton("👁");
        eye.setBounds(330, 90, 50, 30);
        eye.setBackground(Color.WHITE);
        eye.addActionListener(e -> p.setEchoChar(eye.isSelected() ? (char) 0 : echo));

        JButton ok = new JButton("Confirm");
        ok.setBackground(new Color(0,180,0));
        ok.setForeground(Color.WHITE);
        ok.setBorderPainted(false);
        ok.setFocusPainted(false);

        JButton no = new JButton("Cancel");
        no.setBackground(new Color(220, 53, 69));
        no.setForeground(Color.WHITE);
        no.setBorderPainted(false);
        no.setFocusPainted(false);

        ok.setBounds(80, 190, 100, 30);
        no.setBounds(220, 190, 100, 30);


        centerPanel.add(toLbl);
        centerPanel.add(passLbl);
        centerPanel.add(amtLbl);
        centerPanel.add(to);
        centerPanel.add(p);
        centerPanel.add(a);
        centerPanel.add(eye);
        centerPanel.add(ok);
        centerPanel.add(no);


        no.addActionListener(e -> d.dispose());

        ok.addActionListener(e -> {

            if (!new String(p.getPassword()).equals(password)) {
                JOptionPane.showMessageDialog(d, "Wrong Password");
                return;
            }

            double amt;
            try { amt = Double.parseDouble(a.getText()); }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(d, "Invalid Amount");
                return;
            }

            if (amt > balance) {
                JOptionPane.showMessageDialog(d, "Insufficient Balance");
                return;
            }

            if (!creditOther(to.getText(), amt)) {
                JOptionPane.showMessageDialog(d, "Account Not Found");
                return;
            }

            balance -= amt;
            updateAccountFile();
            log("TRANSFER_OUT", amt, "To " + to.getText());

            sendTransactionEmail("Transfer", amt);
            sendReceiverEmail(to.getText(), amt);

            JOptionPane.showMessageDialog(d, "Transfer Successful");
            d.dispose();
        });

        d.setVisible(true);
    }

    // ================= SAO =================
    private void sao() {

        JDialog d = new JDialog(this, "Statement of Account", true);
        d.setSize(600, 400);
        d.setLocationRelativeTo(this);

        JTextArea ta = new JTextArea();
        ta.setEditable(false);

        try (BufferedReader br = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = br.readLine()) != null)
                if (line.contains("," + accountNo + ","))
                    ta.append(line + "\n");
        } catch (Exception e) {
            ta.setText("No transactions found");
        }

        d.add(new JScrollPane(ta));
        d.setVisible(true);
    }

    // ================= FILE / LOG / EMAIL =================
    private void updateAccountFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("accounts.txt"));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d[0].equals(accountNo))
                    d[6] = String.valueOf(balance);
                sb.append(String.join(",", d)).append("\n");
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter("accounts.txt"));
            bw.write(sb.toString());
            bw.close();
        } catch (Exception e) {}
    }

    private boolean creditOther(String acc, double amt) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("accounts.txt"));
            StringBuilder sb = new StringBuilder();
            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d[0].equals(acc)) {
                    d[6] = String.valueOf(Double.parseDouble(d[6]) + amt);
                    log("TRANSFER_IN", amt, "From " + accountNo);
                    found = true;
                }
                sb.append(String.join(",", d)).append("\n");
            }
            br.close();

            BufferedWriter bw = new BufferedWriter(new FileWriter("accounts.txt"));
            bw.write(sb.toString());
            bw.close();

            return found;
        } catch (Exception e) {
            return false;
        }
    }

    private void log(String type, double amt, String detail) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("transactions.txt", true))) {
            String time = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            bw.write(time + "," + accountNo + "," + type + "," + amt + "," + detail);
            bw.newLine();
        } catch (Exception e) {}
    }

    // ================= PROFESSIONAL EMAIL (SENDER) =================
    private void sendTransactionEmail(String type, double amt) {
        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", "587");

            Session s = Session.getInstance(p, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            "mibank.app@gmail.com", "qkub jekj yash vgia");
                }
            });

            Message m = new MimeMessage(s);
            m.setFrom(new InternetAddress("mibank.app@gmail.com"));
            m.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));

            m.setSubject("UET Bank – Transaction Notification");

            m.setText(
                    "Dear " + name + ",\n\n" +
                            "This is to inform you that a " + type.toLowerCase() +
                            " transaction has been successfully processed on your account.\n\n" +
                            "Transaction Details:\n" +
                            "Account Number : " + accountNo + "\n" +
                            "Transaction Type: " + type + "\n" +
                            "Amount          : Rs. " + amt + "\n" +
                            "Available Balance: Rs. " + balance + "\n\n" +
                            "If you did not authorize this transaction, please contact UET Bank immediately.\n\n" +
                            "Regards,\n" +
                            "mi Bank Limited\n\n" +
                            "This is an automated message. Please do not reply."
            );

            Transport.send(m);
        } catch (Exception e) {}
    }

    // ================= PROFESSIONAL EMAIL (RECEIVER) =================
    private void sendReceiverEmail(String toAccount, double amount) {

        try {
            BufferedReader br = new BufferedReader(new FileReader("accounts.txt"));
            String line, rEmail = "", rName = "";

            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d[0].equals(toAccount)) {
                    rName = d[1];
                    rEmail = d[7];
                    break;
                }
            }
            br.close();

            if (rEmail.isEmpty()) return;

            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", "587");

            Session s = Session.getInstance(p, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            "mibank.app@gmail.com", "qkub jekj yash vgia");
                }
            });

            Message m = new MimeMessage(s);
            m.setFrom(new InternetAddress("mibank.app@gmail.com"));
            m.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(rEmail));

            m.setSubject("UET Bank – Credit Alert");

            m.setText(
                    "Dear " + rName + ",\n\n" +
                            "We are pleased to inform you that an amount has been credited to your account.\n\n" +
                            "Transaction Details:\n" +
                            "Credited Amount : Rs. " + amount + "\n" +
                            "Sender Account  : " + accountNo + "\n" +
                            "Sender Name     : " + name + "\n\n" +
                            "If this transaction was not expected, please contact mi Bank immediately.\n\n" +
                            "Regards,\n" +
                            "mi Bank Limited\n\n" +
                            "This is an automated message. Please do not reply."
            );

            Transport.send(m);

        } catch (Exception e) {}
    }
}