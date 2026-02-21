import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.Border;

public class WeaponShop extends JFrame {

    static CardLayout cardLayout;
    static JPanel mainPanel;
    static String loggedAdminName = "";
    static String loggedCustomerID = "";
    static BufferedImage backgroundImage;
    static String userRole = ""; // "admin" or "customer"

    public WeaponShop() {
        super("Weapon Shop System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        try {
            backgroundImage = ImageIO.read(new File("background_login.png"));
        } catch (IOException e) {
            System.out.println("Background image not found.");
        }

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginPanel(), "login");
        mainPanel.add(new RegisterPanel(), "register");
        mainPanel.add(new AdminPanel(), "admin");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");
        setVisible(true);

        DBManager.connect();
    }

    public static void main(String[] args) {
        new WeaponShop();
    }

    // ✅ Utility button creator (place only once)
    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.decode("#1E1E1E")); // dark gray
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(new RoundedBorder(25));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        return button;
    }

    // ✅ Rounded border style class
    static class RoundedBorder implements Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return false;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(new Color(255, 255, 255, 100));
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
