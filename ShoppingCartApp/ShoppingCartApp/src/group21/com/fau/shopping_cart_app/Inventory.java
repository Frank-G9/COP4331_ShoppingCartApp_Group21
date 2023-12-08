package group21.com.fau.shopping_cart_app;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the inventory of products available for purchase.
 */
class Inventory {
    private static final List<Product> products = new ArrayList<>();

    static {
        // Sample
        products.add(new Product(1, "A", 10.0, 20));
        products.add(new Product(2, "B", 15.0, 15));
        products.add(new Product(3, "C", 20.0, 10));
    }

    /**
     * Get the list of products in the inventory.
     *
     * @return The list of products.
     */
    public static List<Product> getProducts() {
        return products;
    }

    /**
     * Get a product by its unique ID.
     *
     * @param productId The product ID to search for.
     * @return The product with the specified ID, or null if not found.
     */
    public static Product getProductById(int productId) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Update the quantity of a product in the inventory.
     *
     * @param productId   The product ID to update.
     * @param newQuantity The new quantity to set.
     */
    public static void updateProductQuantity(int productId, int newQuantity) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                product.setQuantity(newQuantity);
                return;
            }
        }
    }

    /**
     * Add a new product to the inventory.
     *
     * @param product The product to add.
     */
    public static void addProduct(Product product) {
        products.add(product);
    }
}
