package group21.com.fau.shopping_cart_app;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the shopping cart of a user.
 */
class ShoppingCart {
    private static final Map<Product, Integer> cartItems = new HashMap<>();

    /**
     * Get the items in the shopping cart.
     *
     * @return A map of products to quantities in the cart.
     */
    public static Map<Product, Integer> getCartItems() {
        return cartItems;
    }

    /**
     * Add a product to the shopping cart.
     *
     * @param product  The product to add.
     * @param quantity The quantity to add.
     */
    public static void addToCart(Product product, int quantity) {
        if (cartItems.containsKey(product)) {
            int currentQuantity = cartItems.get(product);
            cartItems.put(product, currentQuantity + quantity);
        } else {
            cartItems.put(product, quantity);
        }
    }

    /**
     * Remove a product from the shopping cart.
     *
     * @param product The product to remove.
     */
    public static void removeFromCart(Product product) {
        cartItems.remove(product);
    }

    /**
     * Calculate the total cost of items in the shopping cart.
     *
     * @return The total cost of items in the shopping cart.
     */
    public static double calculateTotal() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
}