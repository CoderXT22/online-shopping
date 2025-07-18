package All;

import java.util.Scanner;


class Admin extends User {
    public Admin(int userId, String username, String email, String password) {
        super(userId, username, email, password, "admin");
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin: " + username);
        System.out.println("Email: " + email);
    }
    
    public static void registerNewAdmin(UserManager userManager) {
        Scanner scanner = new Scanner(System.in);
    
        String adminUsername, adminEmail, adminPassword, confirmPassword;
    
        while (true) {
            System.out.print("Enter new admin username: ");
            adminUsername = scanner.nextLine();
            if (userManager.getUser(adminUsername) != null) {
                System.out.println("Username already exists. Please choose another.");
            } else if (adminUsername.isBlank()) {
                System.out.println("Username cannot be empty.");
            } else {
                break;
            }
        }
    
        while (true) {
            System.out.print("Enter email: ");
            adminEmail = scanner.nextLine();
    
            if (!adminEmail.contains("@") || !adminEmail.contains(".")) {
                System.out.println("Invalid email format. Please enter a valid email.");
            } else {
                break;
            }
        }
        
        while (true) {
            System.out.print("Enter password (min 8 characters): ");
            adminPassword = scanner.nextLine();
    
            if (adminPassword.length() < 8) {
                System.out.println("Password must be at least 8 characters long.");
                continue;
            }
    
            System.out.print("Confirm password: ");
            confirmPassword = scanner.nextLine();
    
            if (adminEmail.isBlank() || adminPassword.isBlank()) {
                System.out.println("Email and password cannot be empty.");
            } else if (!adminPassword.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");
            } else {
                break;
            }
        }
    
        userManager.registerAdmin(adminUsername, adminEmail, adminPassword);
    }
}
