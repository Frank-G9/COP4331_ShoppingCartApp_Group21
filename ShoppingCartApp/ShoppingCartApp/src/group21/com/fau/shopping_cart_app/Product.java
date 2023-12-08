package group21.com.fau.shopping_cart_app;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a product that can be added to the inventory.
 */
class Product {
    private int productId;
    private String name;
    private double price;
    private int quantity;
    private List<Review> reviews = new ArrayList<>();

    /**
     * Constructor to create a Product object.
     *
     * @param productId The unique ID of the product.
     * @param name      The name of the product.
     * @param price     The price of the product.
     * @param quantity  The quantity of the product in stock.
     */
    public Product(int productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Get the product ID.
     *
     * @return The product ID.
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Get the name of the product.
     *
     * @return The product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the price of the product.
     *
     * @return The product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get the quantity of the product in stock.
     *
     * @return The product quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity of the product in stock.
     *
     * @param quantity The new quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get the list of reviews for the product.
     *
     * @return The list of reviews.
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Add a review for the product.
     *
     * @param reviewer The reviewer's name.
     * @param comment  The review comment.
     */
    public void addReview(String reviewer, String comment) {
        Review review = new Review(reviewer, comment);
        reviews.add(review);
    }
}