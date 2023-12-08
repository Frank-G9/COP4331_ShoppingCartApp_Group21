package group21.com.fau.shopping_cart_app;

/**
 * Represents a user in the system.
 */
class User {
    private String username;
    private String password;

    private UserRole role; // New attribute for user role

    /**
     * Enum representing user roles (BUYER or SELLER).
     */
    public enum UserRole {
        BUYER, SELLER
    }

    /**
     * Constructor to create a User object.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @param role     The role of the user (BUYER or SELLER).
     */
    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Get the username of the user.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the password of the user.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the role of the user.
     *
     * @return The user role (BUYER or SELLER).
     */
    public UserRole getRole() {
        return role;
    }
}
