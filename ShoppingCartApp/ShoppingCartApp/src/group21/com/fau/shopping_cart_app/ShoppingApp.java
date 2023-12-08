package group21.com.fau.shopping_cart_app;

import javax.swing.*;
import java.util.Map;

public class ShoppingApp {

    /**
     * Main method to start the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }

    /**
     * Calculate the total cost of items in the shopping cart.
     *
     * @return The total cost of items in the shopping cart.
     */
    public static double calculateTotal() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : ShoppingCart.getCartItems().entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
}