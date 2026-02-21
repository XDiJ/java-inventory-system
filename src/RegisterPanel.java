import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RegisterPanel extends JPanel {
    private JTextField firstNameField, lastNameField, emailField, addressField, licenseIdField;

    public RegisterPanel() {
        setLayout(null);
        setOpaque(false);

        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(180, 80, 440, 360);
        formPanel.setBackground(new Color(0, 0, 0, 160)); // dark gray with transparency

        JLabel title = new JLabel("Register New Customer");
        title.setBounds(100, 10, 300, 30);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        formPanel.add(title);

        firstNameField = createField(formPanel, "First Name:", 60);
        lastNameField = createField(formPanel, "Last Name:", 100);
        emailField = createField(formPanel, "Email:", 140);
        addressField = createField(formPanel, "Address:", 180);
        licenseIdField = createField(formPanel, "License ID:", 220);

        JButton submitButton = WeaponShop.createStyledButton("Submit");
        submitButton.setBounds(240, 280, 120, 35);
        submitButton.addActionListener(e -> registerCustomer());
        formPanel.add(submitButton);

        JButton backButton = WeaponShop.createStyledButton("Back");
        backButton.setBounds(70, 280, 120, 35);
        backButton.addActionListener(e -> WeaponShop.cardLayout.show(WeaponShop.mainPanel, "login"));
        formPanel.add(backButton);

        add(formPanel);
    }

    private JTextField createField(JPanel panel, String label, int y) {
        JLabel lbl = new JLabel(label);
        lbl.setBounds(30, y, 100, 30);
        lbl.setForeground(Color.WHITE);
        panel.add(lbl);

        JTextField tf = new JTextField();
        tf.setBounds(130, y, 250, 30);
        tf.setBackground(Color.decode("#626262"));
        tf.setForeground(Color.WHITE);
        tf.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(tf);

        return tf;
    }

    private void registerCustomer() {
        try {
            Connection conn = DBManager.connect();
            String sql = "INSERT INTO Customer (Customer_ID, F_Name, L_Name, Email, Address, License_ID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, generateCustomerId());
            pst.setString(2, firstNameField.getText());
            pst.setString(3, lastNameField.getText());
            pst.setString(4, emailField.getText());
            pst.setString(5, addressField.getText());
            pst.setString(6, licenseIdField.getText());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Registered successfully! Use Email and License ID to login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            WeaponShop.cardLayout.show(WeaponShop.mainPanel, "login");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateCustomerId() {
        int randomNum = (int)(Math.random() * 900) + 100;
        return "C" + randomNum;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (WeaponShop.backgroundImage != null) {
            g.drawImage(WeaponShop.backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
