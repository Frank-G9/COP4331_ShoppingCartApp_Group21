/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package group21.com.fau.shopping_cart_app;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

public class ShoppingApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }

    public static double calculateTotal() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : ShoppingCart.getCartItems().entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
}

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Product {
    private int productId;
    private String name;
    private double price;
    private int quantity;
    private List<Review> reviews = new ArrayList<>();

    public Product(int productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(String reviewer, String comment) {
        Review review = new Review(reviewer, comment);
        reviews.add(review);
    }
}

class Review {
    private String reviewer;
    private String comment;

    public Review(String reviewer, String comment) {
        this.reviewer = reviewer;
        this.comment = comment;
    }

    public String getReviewer() {
        return reviewer;
    }

    public String getComment() {
        return comment;
    }
}

class Inventory {
    private static final List<Product> products = new ArrayList<>();

    static {
        // Sample
        products.add(new Product(1, "A", 10.0, 20));
        products.add(new Product(2, "B", 15.0, 15));
        products.add(new Product(3, "C", 20.0, 10));
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static Product getProductById(int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    public static void updateProductQuantity(int productId, int newQuantity) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                product.setQuantity(newQuantity);
                return;
            }
        }
    }

    public static void addProduct(Product product) {
        products.add(product);
    }
}

class ShoppingCart {
    private static final Map<Product, Integer> cartItems = new HashMap<>();

    public static Map<Product, Integer> getCartItems() {
        return cartItems;
    }

    public static void addToCart(Product product, int quantity) {
        if (cartItems.containsKey(product)) {
            int currentQuantity = cartItems.get(product);
            cartItems.put(product, currentQuantity + quantity);
        } else {
            cartItems.put(product, quantity);
        }
    }

    public static void removeFromCart(Product product) {
        cartItems.remove(product);
    }

    public static double calculateTotal() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
}

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
    static {
        users.add(new User("customer", "password"));
        users.add(new User("seller", "password"));
    }
}

class ProductBrowseFrame extends JFrame {
    private User user;
    private JLabel totalLabel;
    private JTable inventoryTable;
    private JTextArea reviewTextArea;

    public ProductBrowseFrame(User user) {
        this.user = user;
        setTitle("Product Browse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        inventoryTable = createInventoryTable();
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        reviewTextArea = new JTextArea();
        JScrollPane reviewScrollPane = new JScrollPane(reviewTextArea);
        panel.add(reviewScrollPane, BorderLayout.EAST);

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(e -> addToCart());
        panel.add(addToCartButton, BorderLayout.SOUTH);

        totalLabel = new JLabel("Total: $0.0");
        panel.add(totalLabel, BorderLayout.NORTH);

        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(e -> viewProductDetails());
        panel.add(viewDetailsButton, BorderLayout.WEST);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(e -> checkout());
        panel.add(checkoutButton, BorderLayout.EAST);

        add(panel);
    }

    private JTable createInventoryTable() {
        List<Product> products = Inventory.getProducts();
        Object[][] data = new Object[products.size()][4];

        int i = 0;
        for (Product product : products) {
            data[i][0] = product.getProductId();
            data[i][1] = product.getName();
            data[i][2] = product.getPrice();
            data[i][3] = product.getQuantity();
            i++;
        }

        String[] columnNames = {"ID", "Name", "Price", "Quantity"};

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        return new JTable(tableModel);
    }

    private void addToCart() {
        int selectedRow = inventoryTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to add to the cart");
            return;
        }

        int productId = (int) inventoryTable.getValueAt(selectedRow, 0);
        String productName = (String) inventoryTable.getValueAt(selectedRow, 1);
        double productPrice = (double) inventoryTable.getValueAt(selectedRow, 2);
        int productQuantity = (int) inventoryTable.getValueAt(selectedRow, 3);

        Product selectedProduct = new Product(productId, productName, productPrice, productQuantity);
        int quantityToAdd = askQuantityToAdd(selectedProduct);

        if (quantityToAdd > 0 && quantityToAdd <= productQuantity) {
            ShoppingCart.addToCart(selectedProduct, quantityToAdd);
            JOptionPane.showMessageDialog(this, quantityToAdd + " " + productName + "(s) added to the cart");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a quantity between 1 and " + productQuantity);
        }
        updateCartTotal();
    }

    private void viewProductDetails() {
        int selectedRow = inventoryTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product to view details");
            return;
        }

        int productId = (int) inventoryTable.getValueAt(selectedRow, 0);
        String productName = (String) inventoryTable.getValueAt(selectedRow, 1);
        double productPrice = (double) inventoryTable.getValueAt(selectedRow, 2);
        int productQuantity = (int) inventoryTable.getValueAt(selectedRow, 3);

        Product selectedProduct = Inventory.getProductById(productId);

        if (selectedProduct != null) {
            JOptionPane.showMessageDialog(this, getProductDetails(selectedProduct));
            updateReviewsTextArea(selectedProduct);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid product selection");
        }
    }

    private void updateReviewsTextArea(Product product) {
        StringBuilder reviewsBuilder = new StringBuilder("Seller Reviews:\n");
        for (Review review : product.getReviews()) {
            reviewsBuilder.append("Reviewer: ").append(review.getReviewer()).append("\n")
                          .append("Comment: ").append(review.getComment()).append("\n\n");
        }
        reviewTextArea.setText(reviewsBuilder.toString());
    }

    private String getProductDetails(Product product) {
        return "Product ID: " + product.getProductId() + "\n"
                + "Name: " + product.getName() + "\n"
                + "Price: $" + product.getPrice() + "\n"
                + "Quantity available: " + product.getQuantity();
    }

    private int askQuantityToAdd(Product product) {
        String input = JOptionPane.showInputDialog(this, "Enter quantity for " + product.getName() + ":");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void updateCartTotal() {
        double totalAmount = ShoppingCart.calculateTotal();

        if (totalLabel != null) {
            totalLabel.setText("Total: $" + totalAmount);
        }
    }

    private void checkout() {
        double totalAmount = ShoppingCart.calculateTotal();

        if (totalAmount > 0) {
            // Perform checkout logic (e.g., update inventory, clear the cart, etc.)
            // For now, let's display a message indicating the total amount
            JOptionPane.showMessageDialog(this, "Checkout successful! Total amount: $" + totalAmount);
            ShoppingCart.getCartItems().clear();
            updateCartTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Your cart is empty. Add items before checking out.");
        }
    }
}

class UserRegistrationFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public UserRegistrationFrame() {
        setTitle("User Registration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

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
        panel.add(registerButton);

        add(panel);
    }

    private boolean registerUser(String username, String password) {
        for (User existingUser : LoginFrame.users) {
            if (existingUser.getUsername().equals(username)) {
                return false;
            }
        }

        LoginFrame.users.add(new User(username, password));
        return true;
    }
}
