package All;

import java.io.*;
import java.util.*;
import java.util.HashMap;

class UserManager {
    private HashMap<String, User> users = new HashMap<>();
    private static final String USER_FILE = "users.txt";

    public UserManager() {
        loadUsers();
    }
    
    public Collection<User> getAllUsers() {
        return users.values();
    }

    public void registerUser(String username, String email, String password, String role) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }
        int userId = users.size() + 1;
        User user;
        if (role.equalsIgnoreCase("admin")) {
            user = new Admin(userId, username, email, password);
        } else {
            user = new Customer(userId, username, email, password);
        }
        users.put(username, user);
        saveUsers();
        System.out.println("Registration successful for: " + username);
    }

    public User getUser(String username) {
        return users.get(username);
    }
    
    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE))) {
            for (User user : users.values()) {
                writer.printf("%d,%s,%s,%s,%s\n", user.userId, user.username, user.email, user.password, user.role);
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
    
    public void saveAllUsers() {
        saveUsers();
    }

    private void loadUsers() {
        File file = new File(USER_FILE);
        if (!file.exists()) return;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length < 5) continue;

                int id = Integer.parseInt(parts[0]);
                String username = parts[1];
                String email = parts[2];
                String password = parts[3];
                String role = parts[4];

                User user;
                if (role.equalsIgnoreCase("admin")) {
                    user = new Admin(id, username, email, password);
                } else {
                    user = new Customer(id, username, email, password);
                }
                users.put(username, user);
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }
    
    public void registerCustomer(String username, String email, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }
        int userId = users.size() + 1;
        User user = new Customer(userId, username, email, password);
        users.put(username, user);
        saveUsers(); //
        System.out.println("Customer registered: " + username);
    }

    public void registerAdmin(String username, String email, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }
        int userId = users.size() + 1;
        User user = new Admin(userId, username, email, password);
        users.put(username, user);
        saveUsers(); // 
        System.out.println("Admin registered: " + username);
    }
    // Create default admin: admin/admin123
    public void registerDefaultAdmin() {
        if (!users.containsKey("admin")) {
            User admin = new Admin(1, "admin", "admin@example.com", "admin123");
            users.put("admin", admin);
            saveUsers(); // 
        }
    }
    
    public void editUserProfile(Scanner scanner, User user) {
        System.out.println("\n\t--- Edit Profile ---");
    
        System.out.print("Enter new username (leave blank to keep current): ");
        String newUsername = scanner.nextLine().trim();
    
        System.out.print("Enter new email (leave blank to keep current): ");
        String newEmail = scanner.nextLine().trim();
    
        System.out.print("Enter new password (leave blank to keep current): ");
        String newPassword = scanner.nextLine().trim();
    
        // Password validation
        if (!newPassword.isEmpty()) {
            if (newPassword.length() < 8) {
                System.out.println("Password must be at least 8 characters long. Update cancelled.");
                return;
            }
    
            System.out.print("Confirm new password: ");
            String confirmPassword = scanner.nextLine().trim();
    
            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Update cancelled.");
                return;
            }
    
            user.setPassword(newPassword);
        }
    
        // Username validation
        if (!newUsername.isEmpty() && !newUsername.equals(user.getUsername())) {
            if (users.containsKey(newUsername)) {
                System.out.println("Username already taken. Update cancelled.");
                return;
            }
            users.remove(user.getUsername());
            user.setUsername(newUsername);
        }
    
        // Email validation
        if (!newEmail.isEmpty()) {
            if (!newEmail.contains("@") || !newEmail.contains(".")) {
                System.out.println("Invalid email format. Update cancelled.");
                return;
            }
            user.setEmail(newEmail);
        }
    
        users.put(user.getUsername(), user); // Re-insert in case username changed
        saveUsers();
    
        System.out.println("Profile updated successfully!");
    }
}
