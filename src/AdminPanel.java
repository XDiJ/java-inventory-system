import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    public AdminPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Top panel with title and logout/add buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        topPanel.add(title, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setOpaque(false);

        JButton addWeaponBtn = WeaponShop.createStyledButton("Add Weapon");
        addWeaponBtn.addActionListener(e -> {
            WeaponShop.mainPanel.add(new AddWeaponPanel(), "addWeapon");
            WeaponShop.cardLayout.show(WeaponShop.mainPanel, "addWeapon");
        });

        JButton logoutBtn = WeaponShop.createStyledButton("Logout");
        logoutBtn.addActionListener(e -> WeaponShop.cardLayout.show(WeaponShop.mainPanel, "login"));

        buttonPanel.add(addWeaponBtn);
        buttonPanel.add(logoutBtn);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // You can add other admin components in CENTER if needed
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (WeaponShop.backgroundImage != null) {
            g.drawImage(WeaponShop.backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
