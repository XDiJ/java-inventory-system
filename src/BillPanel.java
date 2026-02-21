import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BillPanel extends JPanel {
    private String weaponName;
    private double pricePerDay;
    private int days;
    private double total;
    private boolean isRent; // true if rent, false if buy

    public BillPanel(String weaponName, double pricePerDay, int days, double total, boolean isRent) {
        this.weaponName = weaponName;
        this.pricePerDay = pricePerDay;
        this.days = days;
        this.total = total;
        this.isRent = isRent;

        setLayout(new GridBagLayout());
        setOpaque(false);

        JPanel billBox = new JPanel(new GridLayout(8, 1, 10, 10));
        billBox.setOpaque(false);

        JLabel titleLabel = createCenteredLabel("***** Receipt *****", 20);
        JLabel nameLabel = createCenteredLabel("Customer: " + WeaponShop.loggedAdminName, 14);
        JLabel typeLabel = createCenteredLabel("Transaction: " + (isRent ? "Rental" : "Purchase"), 14);
        JLabel weaponLabel = createCenteredLabel("Weapon: " + weaponName, 14);
        JLabel priceLabel = createCenteredLabel("Price per Day: " + pricePerDay + " SAR", 14);
        JLabel daysLabel = createCenteredLabel("Days: " + days, 14);
        JLabel totalLabel = createCenteredLabel("Total: " + total + " SAR", 14);

        JButton backButton = WeaponShop.createStyledButton("Back to Shop");
        backButton.addActionListener(e -> WeaponShop.cardLayout.show(WeaponShop.mainPanel, "shop"));

        billBox.add(titleLabel);
        billBox.add(nameLabel);
        billBox.add(typeLabel);
        billBox.add(weaponLabel);
        if (isRent) {
            billBox.add(priceLabel);
            billBox.add(daysLabel);
        }
        billBox.add(totalLabel);
        billBox.add(backButton);

        add(billBox);

        saveBillToFile(); // Save file automatically
    }

    private JLabel createCenteredLabel(String text, int fontSize) {
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        return label;
    }

    private void saveBillToFile() {
        try {
            String billDir = "bills";
            File dir = new File(billDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = billDir + "/Bill-" + WeaponShop.loggedCustomerID + "-" + System.currentTimeMillis() + ".txt";
            FileWriter writer = new FileWriter(fileName);

            writer.write("======================\n");
            writer.write("     Weapon Shop Receipt\n");
            writer.write("======================\n");
            writer.write("Customer Name: " + WeaponShop.loggedAdminName + "\n");
            writer.write("Transaction Type: " + (isRent ? "Rental" : "Purchase") + "\n");
            writer.write("Weapon: " + weaponName + "\n");
            if (isRent) {
                writer.write("Price per Day: " + pricePerDay + " SAR\n");
                writer.write("Number of Days: " + days + "\n");
            }
            writer.write("Total Cost: " + total + " SAR\n");
            writer.write("Date: " + java.time.LocalDate.now() + "\n");
            writer.write("======================\n");
            writer.write("Thank you for choosing WeaponShop!\n");

            writer.close();
        } catch (IOException e) {
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
