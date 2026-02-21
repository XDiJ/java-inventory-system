import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ShopPanel extends JPanel {

    public ShopPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel weaponsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        weaponsPanel.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(weaponsPanel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel title = new JLabel("Welcome, " + WeaponShop.loggedAdminName + " | Available Weapons:");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(title, BorderLayout.WEST);

        JButton logoutButton = WeaponShop.createStyledButton("Logout");
        logoutButton.setPreferredSize(new Dimension(100, 30));
        logoutButton.addActionListener(e -> WeaponShop.cardLayout.show(WeaponShop.mainPanel, "login"));

        if (WeaponShop.userRole.equals("admin")) {
            JButton addWeaponButton = WeaponShop.createStyledButton("Add Weapon");
            addWeaponButton.setPreferredSize(new Dimension(120, 30));
            addWeaponButton.addActionListener(e -> {
                WeaponShop.mainPanel.add(new AddWeaponPanel(), "addWeapon");
                WeaponShop.cardLayout.show(WeaponShop.mainPanel, "addWeapon");
            });

            JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
            topRightPanel.setOpaque(false);
            topRightPanel.add(addWeaponButton);
            topRightPanel.add(logoutButton);
            topPanel.add(topRightPanel, BorderLayout.EAST);
        } else {
            topPanel.add(logoutButton, BorderLayout.EAST);
        }

        try {
            Connection conn = DBManager.connect();
            String sql = "SELECT * FROM Weapons";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                JPanel weaponCard = new JPanel(new BorderLayout());
                weaponCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                weaponCard.setBackground(new Color(0, 0, 0, 150));

                JLabel weaponImage = loadWeaponImage(rs.getString("Weapon_ID"));
                weaponCard.add(weaponImage, BorderLayout.NORTH);

                JPanel infoPanel = new JPanel(new GridLayout(4, 1));
                infoPanel.setOpaque(false);
                infoPanel.add(createLabel("Name: " + rs.getString("Name")));
                infoPanel.add(createLabel("Type: " + rs.getString("Type")));
                infoPanel.add(createLabel("Rental Price: " + rs.getDouble("Rental_price") + " SAR"));
                infoPanel.add(createLabel("Selling Price: " + rs.getDouble("Selling_price") + " SAR"));
                weaponCard.add(infoPanel, BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
                buttonPanel.setOpaque(false);

                String weaponId = rs.getString("Weapon_ID");
                String weaponName = rs.getString("Name");
                double rentalPrice = rs.getDouble("Rental_price");
                double sellingPrice = rs.getDouble("Selling_price");

                if (WeaponShop.userRole.equals("customer")) {
                    JButton rentButton = new JButton("Rent");
                    rentButton.setBackground(Color.decode("#1E1E1E"));
                    rentButton.setForeground(Color.WHITE);

                    JButton buyButton = new JButton("Buy");
                    buyButton.setBackground(Color.decode("#1E1E1E"));
                    buyButton.setForeground(Color.WHITE);

                    rentButton.addActionListener(e -> {
                        WeaponShop.mainPanel.add(new RentFormPanel(weaponId, weaponName, rentalPrice), "rent");
                        WeaponShop.cardLayout.show(WeaponShop.mainPanel, "rent");
                    });

                    buyButton.addActionListener(e -> {
                        buyWeapon(weaponId, weaponName, sellingPrice);
                    });

                    buttonPanel.add(rentButton);
                    buttonPanel.add(buyButton);

                } else if (WeaponShop.userRole.equals("admin")) {
                    JButton removeButton = WeaponShop.createStyledButton("Remove");

                    removeButton.addActionListener(e -> {
                        removeWeapon(weaponId);
                    });

                    buttonPanel.add(removeButton);
                }

                weaponCard.add(buttonPanel, BorderLayout.SOUTH);
                weaponsPanel.add(weaponCard);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JLabel loadWeaponImage(String weaponId) {
        try {
            ImageIcon icon = new ImageIcon("images/" + weaponId + ".png");
            Image img = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(img));
        } catch (Exception e) {
            return new JLabel("No Image", SwingConstants.CENTER);
        }
    }

    private void buyWeapon(String weaponId, String weaponName, double sellingPrice) {
        try {
            Connection conn = DBManager.connect();
            String transactionId = generateShortId("T");

            String sql = "INSERT INTO Transaction (Transaction_ID, Customer_ID, Weapon_ID, Transaction_Type, Amount, Date) VALUES (?, ?, ?, 'Purchase', ?, CURDATE())";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, transactionId);
            pst.setString(2, WeaponShop.loggedCustomerID);
            pst.setString(3, weaponId);
            pst.setDouble(4, sellingPrice);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Successfully Purchased: " + weaponName);
            WeaponShop.mainPanel.add(new BillPanel(weaponName, sellingPrice, 1, sellingPrice, false), "bill");
            WeaponShop.cardLayout.show(WeaponShop.mainPanel, "bill");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing purchase. Please try again.");
        }
    }

    private void removeWeapon(String weaponId) {
        try {
            Connection conn = DBManager.connect();
            String sql = "DELETE FROM Weapons WHERE Weapon_ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, weaponId);

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Weapon Removed Successfully!");
                WeaponShop.mainPanel.remove(this);
                WeaponShop.mainPanel.add(new ShopPanel(), "shop");
                WeaponShop.cardLayout.show(WeaponShop.mainPanel, "shop");
            } else {
                JOptionPane.showMessageDialog(this, "No such Weapon ID found.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to remove weapon.");
        }
    }

    private String generateShortId(String prefix) {
        int randomNum = (int) (Math.random() * 900) + 100;
        return prefix + randomNum;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (WeaponShop.backgroundImage != null) {
            g.drawImage(WeaponShop.backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
}
