import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RentFormPanel extends JPanel {
    private JTextField daysField;
    private JLabel totalCostLabel;
    private double rentalPricePerDay;
    private String weaponName, weaponId;
    
    public RentFormPanel(String weaponId, String weaponName, double rentalPricePerDay) {
        this.weaponId = weaponId;
        this.weaponName = weaponName;
        this.rentalPricePerDay = rentalPricePerDay;
    
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(true);
        panel.setBackground(new Color(0, 0, 0, 180));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
    
        JLabel nameLabel = createLabel("Weapon Name: " + weaponName);
        JLabel priceLabel = createLabel("Rental Price per Day: " + rentalPricePerDay + " SAR");
        JLabel daysLabel = createLabel("Number of Days:");
        totalCostLabel = createLabel("Total Cost: 0 SAR");
    
        daysField = new JTextField(10);
        daysField.setFont(new Font("Arial", Font.BOLD, 14));
        daysField.setBackground(Color.decode("#626262"));
        daysField.setForeground(Color.WHITE);
        daysField.setCaretColor(Color.WHITE);
        daysField.setHorizontalAlignment(JTextField.CENTER);
    
        JButton calcButton = WeaponShop.createStyledButton("Calculate Total");
        JButton confirmButton = WeaponShop.createStyledButton("Confirm Rent");
        JButton cancelButton = WeaponShop.createStyledButton("Cancel");
    
        calcButton.setPreferredSize(new Dimension(150, 40));
        confirmButton.setPreferredSize(new Dimension(150, 40));
        cancelButton.setPreferredSize(new Dimension(150, 40));
    
        calcButton.addActionListener(e -> calculateTotal());
        confirmButton.addActionListener(e -> confirmRent());
        cancelButton.addActionListener(e -> WeaponShop.cardLayout.show(WeaponShop.mainPanel, "shop"));
    
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(nameLabel, gbc);
    
        gbc.gridy++;
        panel.add(priceLabel, gbc);
    
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(daysLabel, gbc);
        gbc.gridx = 1;
        panel.add(daysField, gbc);
    
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(totalCostLabel, gbc);
    
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(calcButton);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
    
        add(panel);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }
    
    private void calculateTotal() {
        try {
            int days = Integer.parseInt(daysField.getText());
            double total = days * rentalPricePerDay;
            totalCostLabel.setText("Total Cost: " + total + " SAR");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of days.");
        }
    }
    
    private void confirmRent() {
        try {
            int days = Integer.parseInt(daysField.getText());
            double total = days * rentalPricePerDay;
            String rentalId = "R" + (int)(Math.random() * 900 + 100);
            String transactionId = "T" + (int)(Math.random() * 900 + 100);
    
            Connection conn = DBManager.connect();
    
            String rentSql = "INSERT INTO Rental (Rental_ID, Total_Cost, Return_status, Start_Date, End_Date, Customer_id) VALUES (?, ?, 'Not Returned', CURDATE(), DATE_ADD(CURDATE(), INTERVAL ? DAY), ?)";
            PreparedStatement rentPst = conn.prepareStatement(rentSql);
            rentPst.setString(1, rentalId);
            rentPst.setDouble(2, total);
            rentPst.setInt(3, days);
            rentPst.setString(4, WeaponShop.loggedCustomerID);
            rentPst.executeUpdate();
    
            String relSql = "INSERT INTO Rental_weapon (Rental_ID, Weapon_ID, Quantity) VALUES (?, ?, 1)";
            PreparedStatement relPst = conn.prepareStatement(relSql);
            relPst.setString(1, rentalId);
            relPst.setString(2, weaponId);
            relPst.executeUpdate();
    
            String transSql = "INSERT INTO Transaction (Transaction_ID, Customer_ID, Weapon_ID, Transaction_Type, Amount, Date) VALUES (?, ?, ?, 'Rental', ?, CURDATE())";
            PreparedStatement transPst = conn.prepareStatement(transSql);
            transPst.setString(1, transactionId);
            transPst.setString(2, WeaponShop.loggedCustomerID);
            transPst.setString(3, weaponId);
            transPst.setDouble(4, total);
            transPst.executeUpdate();
    
            WeaponShop.mainPanel.add(new BillPanel(weaponName, rentalPricePerDay, days, total, true), "bill");
            WeaponShop.cardLayout.show(WeaponShop.mainPanel, "bill");
    
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing rental. Please try again.");
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
