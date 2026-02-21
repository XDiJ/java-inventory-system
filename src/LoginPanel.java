import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.sql.*;

class LoginPanel extends JPanel {
    public LoginPanel() {
        setLayout(null);

        JPanel loginBox = new JPanel(null);
        loginBox.setBounds(200, 120, 400, 330);
        loginBox.setBackground(new Color(0, 0, 0, 160));

        JLabel loginIcon = new JLabel(new ImageIcon("soldier_icon.png"));
        loginIcon.setBounds(160, 10, 104, 64);
        loginBox.add(loginIcon);

        JLabel userLabel = new JLabel("Email / Username:");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(50, 90, 140, 25);
        loginBox.add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(50, 115, 300, 30);
        usernameField.setBackground(Color.decode("#626262"));
        usernameField.setForeground(Color.WHITE);
        usernameField.setBorder(new RoundedBorder(10));
        loginBox.add(usernameField);

        JLabel passLabel = new JLabel("Password / License ID:");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(50, 155, 180, 25);
        loginBox.add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(50, 180, 300, 30);
        passwordField.setBackground(Color.decode("#626262"));
        passwordField.setForeground(Color.WHITE);
        passwordField.setBorder(new RoundedBorder(10));
        loginBox.add(passwordField);

        JButton loginButton = WeaponShop.createStyledButton("Login");
        loginButton.setBounds(50, 230, 130, 35);
        loginBox.add(loginButton);

        JButton registerButton = WeaponShop.createStyledButton("Register");
        registerButton.setBounds(220, 230, 130, 35);
        loginBox.add(registerButton);

        add(loginBox);

        loginButton.addActionListener(e -> {
            String userInput = usernameField.getText().trim();
            String passInput = new String(passwordField.getPassword()).trim();
            attemptLogin(userInput, passInput);
        });

        registerButton.addActionListener(e -> WeaponShop.cardLayout.show(WeaponShop.mainPanel, "register"));
    }

    private void attemptLogin(String username, String password) {
        try {
            Connection conn = DBManager.connect();
            PreparedStatement pst;
            ResultSet rs;
    
            // Try Admin
            String sqlAdmin = "SELECT * FROM Admin WHERE Username=? AND Password=?";
            pst = conn.prepareStatement(sqlAdmin);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
    
            if (rs.next()) {
                WeaponShop.userRole = "admin";
                WeaponShop.loggedAdminName = rs.getString("Name");
                JOptionPane.showMessageDialog(this, "Welcome, " + WeaponShop.loggedAdminName + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                WeaponShop.mainPanel.add(new ShopPanel(), "shop");
                WeaponShop.cardLayout.show(WeaponShop.mainPanel, "shop");
                return; // ✅ Exit after successful admin login
            }
    
            // Try Customer
            String sqlCustomer = "SELECT * FROM Customer WHERE Email=? AND License_ID=?";
            pst = conn.prepareStatement(sqlCustomer);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
    
            if (rs.next()) {
                WeaponShop.userRole = "customer";
                WeaponShop.loggedCustomerID = rs.getString("Customer_ID");
                WeaponShop.loggedAdminName = rs.getString("F_Name") + " " + rs.getString("L_Name");
                JOptionPane.showMessageDialog(this, "Welcome, " + WeaponShop.loggedAdminName + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                WeaponShop.mainPanel.add(new ShopPanel(), "shop");
                WeaponShop.cardLayout.show(WeaponShop.mainPanel, "shop");
                return; // ✅ Exit after successful customer login
            }
    
            // If neither login worked
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (WeaponShop.backgroundImage != null) {
            g.drawImage(WeaponShop.backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
