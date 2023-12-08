package group21.com.fau.shopping_cart_app;

/**
 * Represents a review for a product.
 */
class Review {
    private String reviewer;
    private String comment;

    /**
     * Constructor to create a Review object.
     *
     * @param reviewer The reviewer's name.
     * @param comment  The review comment.
     */
    public Review(String reviewer, String comment) {
        this.reviewer = reviewer;
        this.comment = comment;
    }

    /**
     * Get the reviewer's name.
     *
     * @return The reviewer's name.
     */
    public String getReviewer() {
        return reviewer;
    }

    /**
     * Get the review comment.
     *
     * @return The review comment.
     */
    public String getComment() {
        return comment;
    }
}
