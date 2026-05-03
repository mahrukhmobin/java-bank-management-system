import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class RemoveClientDialog extends JDialog {

    JTextField accField, phoneField, cnicField;

    public RemoveClientDialog(JFrame parent) {
        super(parent, "Remove Client", true);
        setSize(600, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // TOP PANEL - Green Header

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 100, 80));
        topPanel.setPreferredSize(new Dimension(600, 80));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 25));

        JLabel titleLabel = new JLabel("REMOVE CLIENT ACCOUNT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);

        add(topPanel, BorderLayout.NORTH);

        // CENTER PANEL - Form Fields

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);
        centerPanel.setBackground(Color.WHITE);

        int labelX = 80;
        int fieldX = 250;
        int y = 40;
        int gap = 70;

        // Account Number
        JLabel accLbl = new JLabel("Account Number:");
        accLbl.setBounds(labelX, y, 150, 30);
        accLbl.setFont(new Font("Arial", Font.BOLD, 15));
        centerPanel.add(accLbl);

        accField = new JTextField();
        accField.setBounds(fieldX, y, 250, 35);
        accField.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(accField);
        y += gap;

        // Phone Number
        JLabel phoneLbl = new JLabel("Phone Number:");
        phoneLbl.setBounds(labelX, y, 150, 30);
        phoneLbl.setFont(new Font("Arial", Font.BOLD, 15));
        centerPanel.add(phoneLbl);

        phoneField = new JTextField();
        phoneField.setBounds(fieldX, y, 250, 35);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(phoneField);
        y += gap;

        // CNIC
        JLabel cnicLbl = new JLabel("CNIC:");
        cnicLbl.setBounds(labelX, y, 150, 30);
        cnicLbl.setFont(new Font("Arial", Font.BOLD, 15));
        centerPanel.add(cnicLbl);

        cnicField = new JTextField();
        cnicField.setBounds(fieldX, y, 250, 35);
        cnicField.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(cnicField);

        add(centerPanel, BorderLayout.CENTER);

        // BOTTOM PANEL - Buttons

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(600, 70));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        JButton cancelBtn = new JButton("CANCEL");
        cancelBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cancelBtn.setPreferredSize(new Dimension(130, 40));
        cancelBtn.setBackground(new Color(200, 200, 200));
        cancelBtn.setBorderPainted(false);
        cancelBtn.setFocusPainted(false);

        JButton removeBtn = new JButton("REMOVE");
        removeBtn.setFont(new Font("Arial", Font.BOLD, 14));
        removeBtn.setPreferredSize(new Dimension(130, 40));
        removeBtn.setBackground(new Color(220, 53, 69)); // Red color
        removeBtn.setForeground(Color.WHITE);
        removeBtn.setBorderPainted(false);
        removeBtn.setFocusPainted(false);

        bottomPanel.add(cancelBtn);
        bottomPanel.add(removeBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button Actions

        cancelBtn.addActionListener(e -> dispose());

        removeBtn.addActionListener(e -> {
            removeClient();
        });

        setVisible(true);
    }

    // ---------------- REMOVE CLIENT LOGIC ----------------

    private void removeClient() {

        String accNo = accField.getText().trim();
        String phone = phoneField.getText().trim();
        String cnic  = cnicField.getText().trim();

        // Validation
        if (accNo.isEmpty() || phone.isEmpty() || cnic.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all fields",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        File input = new File("accounts.txt");
        File temp = new File("temp.txt");

        boolean found = false;
        String email = "";
        String name = "";

        try (
                BufferedReader br = new BufferedReader(new FileReader(input));
                BufferedWriter bw = new BufferedWriter(new FileWriter(temp))
        ) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data[0].equals(accNo) &&
                        data[2].equals(phone) &&
                        data[3].equals(cnic)) {

                    name = data[1];
                    email = data[7];
                    found = true;
                    continue;
                }

                bw.write(line);
                bw.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error accessing account file",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!found) {
            temp.delete();
            JOptionPane.showMessageDialog(this,
                    "Invalid client details. Account not found.",
                    "Not Found",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        input.delete();
        temp.renameTo(input);

        sendEmail(email, accNo, name);

        JOptionPane.showMessageDialog(
                this,
                "Client account removed successfully!\nNotification email sent to client.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }

    // ---------------- EMAIL ----------------

    private void sendEmail(String to, String accNo, String name) {

        final String from = "mibank.app@gmail.com";
        final String password = "qkub jekj yash vgia";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject("Account Closure Notification - MI Bank Limited");

            msg.setText(
                    "Dear " + name + ",\n\n" +
                            "This is to inform you that your account (" + accNo + ") has been closed " +
                            "and removed from mi Bank Limited as per your request or bank policy.\n\n" +
                            "If you have any questions or concerns, please contact our customer service.\n\n" +
                            "Thank you for banking with us.\n\n" +
                            "Regards,\n" +
                            "mi Bank Limited\n\n" +
                            "This is an automated message. Please do not reply."
            );

            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}