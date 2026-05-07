# Java Bank Management System

A Java-based desktop banking application developed as an OOP Final Project — simulating real-world banking operations through a structured, GUI-based system with separate client and employee modules.

---

## Project Overview

This application implements a complete bank management system using core Object-Oriented Programming concepts including classes, objects, encapsulation, and file handling.

---

## Features

**Client Module**
- Account creation with strong validation (name, phone, CNIC, email, password)
- Uniqueness checks for username, phone, and email
- Secure login with password visibility toggle
- Deposit and withdrawal with password confirmation
- Fund transfer to other accounts
- Statement of Account (SOA) generation
- Show/hide balance toggle

**Employee Module**
- Secure employee login with fixed credentials
- View all bank accounts with details
- Remove client accounts with verification
- Bank assets overview — total balance across all accounts

**General**
- Automated email notifications for account creation, transactions, and account closure
- File-based data persistence (accounts.txt, transactions.txt)
- Smooth slide-in navigation animations
- Clean and intuitive GUI built with Java Swing

---

## Tech Stack

| Tool | Purpose |
|------|---------|
| Java | Core language |
| Java Swing | Desktop GUI |
| JavaMail API | Automated email notifications |
| File Handling | Data persistence |
| OOP Principles | System design and structure |

---

## Project Structure

```
BankManagementSystem/
│
├── src/
│   ├── WelcomeUI.java            # Entry point and main screen
│   ├── ClientPage.java           # Client landing page
│   ├── SignInPage.java           # Client login
│   ├── OpenAccountPage.java      # New account registration
│   ├── ClientDashboard.java      # Client banking operations
│   ├── EmployeeLoginPage.java    # Employee login
│   ├── EmployeeDashboard.java    # Employee panel
│   ├── BankAccountsDialog.java   # View all accounts
│   ├── AssetsDialog.java         # Bank assets overview
│   ├── RemoveClientDialog.java   # Remove client account
│   └── UIUtils.java              # Slide animation utility
│
├── lib/
│   ├── javax.mail.jar
│   └── javax.activation.jar
│
├── accounts.txt                  # Stores account data
├── transactions.txt              # Stores transaction logs
└── README.md
```

---

## How to Run

**Prerequisites:**
- Java JDK 8 or above
- IntelliJ IDEA or any Java IDE

**Steps:**
```
1. Clone the repo
2. Open in IntelliJ IDEA
3. Go to File → Project Structure → Libraries
4. Add javax.mail.jar and javax.activation.jar from the lib/ folder
5. Run WelcomeUI.java
```

---

## OOP Concepts Applied

- Encapsulation — data fields kept private, accessed via methods
- Classes and Objects — each module is a separate class
- File Handling — BufferedReader/Writer for persistent storage
- Event-driven programming — ActionListeners for all UI interactions
- Modular design — each feature in its own class/dialog

---

*Built by [Mahrukh Mobin](https://github.com/mahrukhmobin) — Computer Engineering Student @ UET Lahore*
