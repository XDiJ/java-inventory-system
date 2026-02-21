import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RemoveWeaponPanel extends JPanel {
    JTextField weaponIdField;

    public RemoveWeaponPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(true);
        formPanel.setBackground(new Color(0, 0, 0, 150));

        weaponIdField = createField();

        formPanel.add(createLabel("Weapon ID to Remove:"));
        formPanel.add(weaponIdField);

        JButton removeButton = WeaponShop.createStyledButton("Remove Weapon");
        JButton backButton = WeaponShop.createStyledButton("Back");

        removeButton.addActionListener(e -> removeWeapon());
        backButton.addActionListener(e -> WeaponShop.cardLayout.show(WeaponShop.mainPanel, "admin"));

        formPanel.add(backButton);
        formPanel.add(removeButton);

        add(formPanel);
    }

    private JTextField createField() {
        JTextField field = new JTextField();
        field.setBackground(Color.decode("#626262"));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        return field;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private void removeWeapon() {
        try {
            Connection conn = DBManager.connect();
            String sql = "DELETE FROM Weapons WHERE Weapon_ID=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, weaponIdField.getText());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(this, "Weapon Removed Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No such Weapon ID found.");
            }

            WeaponShop.cardLayout.show(WeaponShop.mainPanel, "admin");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to remove weapon.");
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
