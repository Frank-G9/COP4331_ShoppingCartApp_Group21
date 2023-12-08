package group21.com.fau.shopping_cart_app;

import javax.swing.*;
import java.awt.*;

public class UserRegistrationFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private JComboBox<User.UserRole> roleComboBox;

    public UserRegistrationFrame() {
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 4, 4, 4); // top, left, bottom, right padding

        // Username label and text field
        gbc.gridx = 0; // column
        gbc.gridy = 0; // row
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        usernameField = new JTextField(10);
        panel.add(usernameField, gbc);

        // Password label and text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField(10);
        panel.add(passwordField, gbc);

        // Role label and combo box
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        roleComboBox = new JComboBox<>(User.UserRole.values());
        panel.add(roleComboBox, gbc);

        // Register button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across two columns
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (registerUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Account created successfully");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username already exists. Please choose another.");
            }
        });
        panel.add(registerButton, gbc);

        add(panel);
    }

    private boolean registerUser(String username, String password) {
        for (User existingUser : LoginFrame.users) {
            if (existingUser.getUsername().equals(username)) {
                return false;
            }
        }

        User.UserRole selectedRole = (User.UserRole) roleComboBox.getSelectedItem();
        LoginFrame.users.add(new User(username, password, selectedRole)); // Include role in user creation
        return true;
    }
}


