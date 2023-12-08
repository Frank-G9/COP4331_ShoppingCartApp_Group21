package group21.com.fau.shopping_cart_app;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the login frame for the application.
 */
class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User user = authenticate(username, password);

            if (user != null) {
                openProductBrowseWindow(user);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        });
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> openUserRegistrationWindow());
        panel.add(registerButton);

        add(panel);
    }

    private User authenticate(String username, String password) {
        // Hardcoded user authentication (replace with actual authentication logic)
        for (User existingUser : users) {
            if (existingUser.getUsername().equals(username) && existingUser.getPassword().equals(password)) {
                return existingUser;
            }
        }
        return null;
    }

    private void openProductBrowseWindow(User user) {
        ProductBrowseFrame productBrowseFrame = new ProductBrowseFrame(user);
        productBrowseFrame.setVisible(true);
    }

    private void openUserRegistrationWindow() {
        UserRegistrationFrame registrationFrame = new UserRegistrationFrame();
        registrationFrame.setVisible(true);
    }

    // Sample hardcoded users for simplicity (replace with database storage)
    static final List<User> users = new ArrayList<>();
}
