import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.sql.*;

public class AddWeaponPanel extends JPanel {

    private JTextField idField, nameField, sellPriceField, rentPriceField, typeField, caliberField;
    private JLabel imagePathLabel;
    private File selectedImageFile = null;

    public AddWeaponPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        idField = createField();
        nameField = createField();
        sellPriceField = createField();
        rentPriceField = createField();
        typeField = createField();
        caliberField = createField();
        imagePathLabel = new JLabel("No image selected");
        imagePathLabel.setForeground(Color.WHITE);

        addRow(gbc, 0, "Weapon ID:", idField);
        addRow(gbc, 1, "Name:", nameField);
        addRow(gbc, 2, "Selling Price:", sellPriceField);
        addRow(gbc, 3, "Rental Price:", rentPriceField);
        addRow(gbc, 4, "Type:", typeField);
        addRow(gbc, 5, "Caliber:", caliberField);

        // Browse image
        gbc.gridx = 0;
        gbc.gridy = 6;
        add(createStyledLabel("Add pic:"), gbc);
        gbc.gridx = 1;
        JButton browseBtn = WeaponShop.createStyledButton("Browse");
        browseBtn.setPreferredSize(new Dimension(120, 30));
        browseBtn.addActionListener(e -> chooseImage());
        JPanel imgPanel = new JPanel(new BorderLayout());
        imgPanel.setOpaque(false);
        imgPanel.add(imagePathLabel, BorderLayout.CENTER);
        imgPanel.add(browseBtn, BorderLayout.EAST);
        add(imgPanel, gbc);

        // Buttons
        gbc.gridy = 7;
        gbc.gridx = 0;
        JButton back = WeaponShop.createStyledButton("Back");
        back.addActionListener(e -> WeaponShop.cardLayout.show(WeaponShop.mainPanel, "shop"));
        add(back, gbc);

        gbc.gridx = 1;
        JButton confirm = WeaponShop.createStyledButton("Confirm");
        confirm.addActionListener(e -> insertWeapon());
        add(confirm, gbc);
    }

    private void addRow(GridBagConstraints gbc, int y, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = y;
        add(createStyledLabel(label), gbc);
        gbc.gridx = 1;
        add(field, gbc);
    }

    private JTextField createField() {
        JTextField tf = new JTextField(15);
        tf.setBackground(Color.decode("#626262"));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setFont(new Font("Arial", Font.PLAIN, 14));
        return tf;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Weapon Image");
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = chooser.getSelectedFile();
            imagePathLabel.setText(selectedImageFile.getName());
        }
    }

    private void insertWeapon() {
        try {
            if (selectedImageFile == null || idField.getText().isEmpty() || nameField.getText().isEmpty()
                    || sellPriceField.getText().isEmpty() || rentPriceField.getText().isEmpty()
                    || typeField.getText().isEmpty() || caliberField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields and select an image.");
                return;
            }
    
            Connection conn = DBManager.connect();
            String sql = "INSERT INTO Weapons (Name, Weapon_ID, Selling_price, Rental_price, Caliber, Type, Admin_id, Availability_status, License_requirement) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
    
            pst.setString(1, nameField.getText());
            pst.setString(2, idField.getText());
            pst.setDouble(3, Double.parseDouble(sellPriceField.getText()));
            pst.setDouble(4, Double.parseDouble(rentPriceField.getText()));
            pst.setString(5, caliberField.getText());
            pst.setString(6, typeField.getText());
            pst.setString(7, "A101"); // Replace with actual admin ID logic if needed
            pst.setString(8, "Available"); // Default status
            pst.setString(9, "Yes"); // Assuming all weapons require a license
    
            pst.executeUpdate();
    
            // Save image
            String imagePath = "images/" + idField.getText() + ".png";
            Files.copy(selectedImageFile.toPath(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
    
            JOptionPane.showMessageDialog(this, "Weapon added and image saved successfully!");
            WeaponShop.mainPanel.add(new ShopPanel(), "shop");
            WeaponShop.cardLayout.show(WeaponShop.mainPanel, "admin");
    
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting weapon. Check inputs.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save image.");
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
