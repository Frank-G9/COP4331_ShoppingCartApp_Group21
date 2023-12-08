package group21.com.fau.shopping_cart_app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

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

        // Panel for top buttons
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        topButtonPanel.add(logoutButton);

        if (user.getRole() == User.UserRole.SELLER) {
            JButton addProductButton = new JButton("Add New Product");
            addProductButton.addActionListener(e -> addNewProduct());
            topButtonPanel.add(addProductButton);
        }

        // Adding the top button panel to the NORTH region
        panel.add(topButtonPanel, BorderLayout.NORTH);

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

        Product selectedProduct = Inventory
                .getProductById(productId);

        if (selectedProduct != null) {
            JOptionPane.showMessageDialog(this, getProductDetails(selectedProduct));
            updateReviewsTextArea(selectedProduct);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid product selection");
        }
        int response = JOptionPane.showConfirmDialog(this, getProductDetails(selectedProduct) +
                "\n\nWould you like to leave a review?", "Product Details", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            String review = JOptionPane.showInputDialog("Enter your review:");
            if (review != null && !review.trim().isEmpty()) {
                selectedProduct.addReview(user.getUsername(), review);
            }
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
        StringBuilder details = new StringBuilder();
        details.append("Product ID: ").append(product.getProductId()).append("\n")
                .append("Name: ").append(product.getName()).append("\n")
                .append("Price: $").append(product.getPrice()).append("\n")
                .append("Quantity available: ").append(product.getQuantity()).append("\n\n")
                .append("Reviews:\n");

        for (Review review : product.getReviews()) {
            details.append("Reviewer: ").append(review.getReviewer()).append("\n")
                    .append("Comment: ").append(review.getComment()).append("\n\n");
        }

        return details.toString();
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

    private void addNewProduct() {
        if (user.getRole() != User.UserRole.SELLER) {
            JOptionPane.showMessageDialog(this, "Only sellers can add products.");
            return;
        }

        // Example of gathering product details (You should implement a more robust method)
        String name = JOptionPane.showInputDialog("Enter product name:");
        double price = Double.parseDouble(JOptionPane.showInputDialog("Enter product price:"));
        int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter product quantity:"));

        // Generate a new product ID
        int newProductId = Inventory.getProducts().size() + 1;

        Inventory.addProduct(new Product(newProductId, name, price, quantity));
        JOptionPane.showMessageDialog(this, "Product added successfully.");

        refreshInventoryTable();
    }

    private void refreshInventoryTable() {
        DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
        model.setRowCount(0); // Clear existing data

        for (Product product : Inventory.getProducts()) {
            model.addRow(new Object[]{product.getProductId(), product.getName(), product.getPrice(), product.getQuantity()});
        }
    }

    private void checkout() {
        double totalAmount = ShoppingCart.calculateTotal();

        if (totalAmount > 0) {
            for (Map.Entry<Product, Integer> entry : ShoppingCart.getCartItems().entrySet()) {
                Product product = entry.getKey();
                int purchasedQuantity = entry.getValue();
                int newQuantity = product.getQuantity() - purchasedQuantity;
                Inventory.updateProductQuantity(product.getProductId(), newQuantity);
            }

            ShoppingCart.getCartItems().clear();
            updateCartTotal();
            JOptionPane.showMessageDialog(this, "Checkout successful! Total amount: $" + totalAmount);
        } else {
            JOptionPane.showMessageDialog(this, "Your cart is empty. Add items before checking out.");
        }

        refreshInventoryTable();
    }

    private void logout() {
        this.dispose(); // Close the current window
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true); // Open the login window
    }
}

