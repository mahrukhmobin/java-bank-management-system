import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class OpenAccountPage extends JFrame {



    public OpenAccountPage() {

        setTitle("Open New Account");
        setSize(1920, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Background Image

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
            ClientPage page = new ClientPage();
            UIUtils.slideInFromRight(page);
            dispose();

        });

        // FORM LABELS & FIELDS

        int labelX = 390;
        int fieldX = 700;
        int y = 290;
        int gap = 55;

        JTextField nameField = createField(background, "Name:", labelX, fieldX, y);
        y += gap;

        JTextField phoneField = createField(background, "Phone Number:", labelX, fieldX, y);
        y += gap;

        JTextField emailField = createField(background, "Email:", labelX, fieldX, y);
        y += gap;

        JTextField cnicField = createField(background, "CNIC:", labelX, fieldX, y);
        y += gap;

        JTextField userField = createField(background, "Username:", labelX, fieldX, y);
        y += gap;

        JPasswordField passField = new JPasswordField();
        createLabel(background, "Set Password:", labelX, y);
        passField.setBounds(fieldX, y, 240, 40);
        background.add(passField);
        y += gap;

        JToggleButton eye = new JToggleButton("👁");
        eye.setBounds(948,565,50,40);
        eye.setBackground(Color.WHITE);
        background.add(eye);

        eye.addActionListener(e -> {
            if (eye.isSelected())
                passField.setEchoChar((char) 0);
            else
                passField.setEchoChar('●');
        });


        JTextField balanceField = createField(background, "Initial Balance:", labelX, fieldX, y);

        // CREATE ACCOUNT BUTTON

        JButton createBtn = new JButton("CREATE ACCOUNT");
        createBtn.setBounds(500, y + 80, 250, 40);
        createBtn.setFont(new Font("Arial", Font.BOLD, 16));
        createBtn.setBackground(new Color(255, 255, 255));
        createBtn.setOpaque(true);
        createBtn.setBorderPainted(false);
        background.add(createBtn);

        // VALIDATION LOGIC

        createBtn.addActionListener(e -> {

            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String cnic = cnicField.getText().trim();
            String email = emailField.getText().trim();
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());
            String balance = balanceField.getText().trim();

            // Name validation
            if (!name.matches("[a-zA-Z ]+")) {
                showError("Name must contain only alphabets");
                return;
            }

            // Phone validation
            if (!phone.matches("\\d{11}")) {
                showError("Phone number must be 11 digits");
                return;
            }
            // Phone uniqueness check
            if (phoneExists(phone)) {
                showError("Phone number already registered");
                return;
            }

            // Email validation
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                showError("Invalid email format");
                return;
            }

// Email uniqueness check
            if (emailExists(email)) {
                showError("Email already registered");
                return;
            }



            // CNIC validation
            if (!cnic.matches("\\d{13}")) {
                showError("CNIC must be 13 digits");
                return;
            }

            // Username uniqueness (FILE BASED)
            if (usernameExists(username)) {
                showError("Username already exists");
                return;
            }

            // Password validation
            if (!password.matches("^(?=.*[@#$%!^&*]).{8,}$")) {
                showError("Password must be 8 characters and contain a special character");
                return;
            }

            // Balance validation
            if (!balance.matches("\\d+")) {
                showError("Initial balance must be numeric");
                return;
            }

            // SAVE DATA
            String accountNo = generateAccountNumber();

            String record = accountNo + "," + name + "," + phone + "," +
                    cnic + "," + username + "," + password + "," + balance+","+email ;


            saveAccount(record);
            sendEmail(email, name, accountNo, username, balance);


            JOptionPane.showMessageDialog(
                    this,
                    "Account Created Successfully!\nYour Account Number is: " + accountNo
            );



            // OPEN SIGN IN PAGE
            ClientPage page = new ClientPage();
            UIUtils.slideInFromRight(page);
            dispose();

        });


        setVisible(true);

    }

    // Helper Methods


    private void sendEmail(String toEmail, String name, String accountNo,
                           String username, String balance) {

        final String fromEmail = "mibank.app@gmail.com";
        final String password = "qkub jekj yash vgia";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );
            message.setSubject("Bank Account Created Successfully");

            message.setText(
                    "Dear " + name + ",\n\n" +
                            "Your bank account has been created successfully.\n\n" +
                            "Account Number: " + accountNo + "\n" +
                            "Username: " + username + "\n" +
                            "Initial Balance: Rs. " + balance + "\n\n" +
                            "Regards,\n"+
                            "mi Bank Limited\n\n"+
                            "This is an automated message. Please do not reply."
            );

            Transport.send(message);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Account created but email could not be sent.",
                    "Email Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }


    // Generate next account number (A1001, A1002, ...)
    private String generateAccountNumber() {
        int lastNumber = 1000;

        try {
            File file = new File("accounts.txt");
            if (!file.exists()) {
                return "A1001";
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line, lastLine = null;

            while ((line = br.readLine()) != null) {
                lastLine = line;
            }
            br.close();

            if (lastLine != null) {
                String[] data = lastLine.split(",");
                String lastAcc = data[0]; // Account number
                lastNumber = Integer.parseInt(lastAcc.substring(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "A" + (lastNumber + 1);
    }

    private JTextField createField(JLabel bg, String text, int lx, int fx, int y) {
        createLabel(bg, text, lx, y);
        JTextField field = new JTextField();
        field.setBounds(fx, y, 300, 40);
        bg.add(field);
        return field;
    }

    private void createLabel(JLabel bg, String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 220, 40);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        bg.add(label);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }


    // Check if phone number already exists in file
    private boolean phoneExists(String phone) {
        try {
            File file = new File("accounts.txt");
            if (!file.exists()) return false;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // data[1] = phone number
                if (data[2].equals(phone)) {
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // Check if username already exists in file
    private boolean usernameExists(String username) {
        try {
            File file = new File("accounts.txt");
            if (!file.exists()) return false;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[4].equals(username)) {
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Check if email already exists in file
    private boolean emailExists(String email) {
        try {
            File file = new File("accounts.txt");
            if (!file.exists()) return false;

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[7].equalsIgnoreCase(email)) {
                    br.close();
                    return true;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // Save account info to file
    private void saveAccount(String record) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("accounts.txt", true));
            bw.write(record);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}