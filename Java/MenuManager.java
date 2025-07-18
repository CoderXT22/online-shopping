package All;

import java.util.Scanner;

public class MenuManager {
    

    public static void main(String[] args) {
        System.out.print("\t  ______    _  _ \n");
        System.out.print("\t |  ____|  | || |\n");
        System.out.print("\t | |__     | || |\n");
        System.out.print("\t |  __|    |__   _|\n");
        System.out.print("\t | |          | |\n");
        System.out.print("\t |_|          |_|\n");
        System.out.print("\t HEADPHONE STORE\n");
        System.out.print("\n");
    }
    
    public static void showMainMenu(Scanner scanner, UserManager userManager, Authentication authService) {
        int mainChoice;
        do {
            main(null);
            System.out.println("\t1. Login");
            System.out.println("\t2. Register");
            System.out.println("\t3. Exit\n");
            mainChoice = Main.readIntInput(scanner, "Enter your choice: ", 1, 3);
            switch (mainChoice) {
                case 1 -> loginUser(scanner, userManager, authService);
                case 2 -> Customer.register(userManager);
                case 3 -> {
                    Customer customer = Order.getCurrentCustomer();
                    if (customer != null) {
                        customer.getShoppingCart().saveCartToCSV(customer.getUsername());
                    }

                    System.out.println("Thank you for supporting F4. Goodbye!");
                    thankyou();
                }
                    
                default -> System.out.println("Invalid choice.");
            }
        } while (mainChoice != 3);
    }

    public static void loginUser(Scanner scanner, UserManager userManager, Authentication authService) {
        System.out.print("Enter username: ");
        String loginUser = scanner.nextLine().trim();
    
        if (loginUser.isBlank()) {
            System.out.println("Username cannot be empty.");
            return;
        }
    
        User existing = userManager.getUser(loginUser);
        if (existing == null) {
            handleUserNotFound(scanner, userManager);
            return;
        }
    
        handlePasswordLogin(scanner, userManager, authService, existing);
    }

    private static void handleUserNotFound(Scanner scanner, UserManager userManager) {
        System.out.println("Username not found.");
        while (true) {
            System.out.print("Would you like to register as a customer? (yes/no): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("yes") || answer.equals("y")) {
                Customer.register(userManager);
                return;
            } else if (answer.equals("no") || answer.equals("n")) {
                System.out.println("Returning to User Menu...");
                return;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    private static void handlePasswordLogin(Scanner scanner, UserManager userManager, Authentication authService, User existing) {
        boolean firstAttempt = true;
    
        while (true) {
            System.out.print("Enter password: ");
            String loginPass = scanner.nextLine().trim();
    
            if (loginPass.isEmpty()) {
                System.out.println("Password cannot be empty.");
                continue;
            }
    
            if (!existing.getPassword().equals(loginPass)) {
                System.out.println("Incorrect password.");
                if (firstAttempt) {
                    firstAttempt = false;
                    System.out.print("Forgot your password? (yes/no): ");
                    String forgot = scanner.nextLine().trim().toLowerCase();
                    if (forgot.equals("yes") || forgot.equals("y")) {
                        handlePasswordReset(scanner, userManager, existing);
                        return;
                    }
                }
            } else {
                User currentUser = authService.login(existing.getUsername(), loginPass);
                if (currentUser != null) {
                    ProductApp.setUser(currentUser);
                    loggedInMenu(scanner, currentUser, userManager);
                }
                break;
            }
        }
    }
    
    private static void handlePasswordReset(Scanner scanner, UserManager userManager, User user) {
        System.out.println("--- Password Reset ---");

        System.out.print("Enter your registered email: ");
        String inputEmail = scanner.nextLine().trim();

        if (!user.getEmail().equalsIgnoreCase(inputEmail)) {
            System.out.println("Email does not match our records. Password reset cancelled.");
            return;
        }

        String newPassword;
        while (true) {
            System.out.print("Enter new password (at least 8 characters): ");
            newPassword = scanner.nextLine().trim();

            if (newPassword.length() < 8) {
                System.out.println("Password must be at least 8 characters long.");
                continue;
            }

            System.out.print("Confirm new password: ");
            String confirmPassword = scanner.nextLine().trim();

            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Try again.");
            } else {
                break;
            }
        }

        user.setPassword(newPassword);
        userManager.saveAllUsers();
        System.out.println("Password reset successful. Please log in again.");
    }


    public static void loggedInMenu(Scanner scanner, User user, UserManager userManager) {
        int option;
        boolean isRunning = true;

        while (isRunning) {
            main(null);
            System.out.println("------ Welcome, " + user.getUsername() + " (" + user.getRole() + ") ------\n");
            System.out.println("\t1. View Profile");

            if (user.getRole().equals("admin")) {
                
                System.out.println("\t2. Edit Profile");
                System.out.println("\t3. Register New Admin");
                System.out.println("\t4. Manage Products");
                System.out.println("\t5. View All Order");
                System.out.println("\t6. Generate Monthly Sales Report ");
                System.out.println("\t7. Generate Daily Sales Report ");
                System.out.println("\t8. Generate User Report");
                System.out.println("\t9. Logout\n");
            } else {
                System.out.println("\t2. Edit Profile");
                System.out.println("\t3. Product Dashboard");
                System.out.println("\t4. View Cart");
                System.out.println("\t5. View Order History");
                System.out.println("\t6. Logout\n");
            }

            option = Main.readIntInput(scanner, "Enter your choice: ", 1, 9);

            if (user.getRole().equals("admin")) {
                switch (option) {
                    case 1 -> {
                        user.displayInfo();
                        pauseToReturn(scanner);
                    }
                    case 2 -> {
                        String confirm;
                        while (true) {
                            System.out.print("Are you sure you want to edit profile (y/n): ");
                            confirm = scanner.nextLine().trim().toLowerCase();
                            if (confirm.equals("yes") || confirm.equals("y")) {
                                userManager.editUserProfile(scanner, user);
                                break;
                            } else if (confirm.equals("no") || confirm.equals("n")) {
                                    System.out.println("Cancelled edit profile.");
                                    break;
                                } else {
                                    System.out.println("Invalid input. Please enter 'y/n'.");
                                }
                        }
                    }
                    case 3 -> {
                        String confirm;
                            while (true) {
                                System.out.print("Are you sure you want to add a new admin? (y/n): ");
                                confirm = scanner.nextLine().trim().toLowerCase();
                                if (confirm.equals("yes") || confirm.equals("y")) {
                                    Admin.registerNewAdmin(userManager);
                                    break;
                                } else if (confirm.equals("no") || confirm.equals("n")) {
                                    System.out.println("Cancelled adding new admin.");
                                    break;
                                } else {
                                    System.out.println("Invalid input. Please enter 'y/n'.");
                                }
                            }
                    }
                    case 4 -> {
                        ProductManagement.showProductManagement();
                    }
                    case 5 -> {
                        Report.viewAllOrders();
                        pauseToReturn(scanner);
                    }
                    case 6 -> {
                        Report.generateMonthlySalesReport();
                        pauseToReturn(scanner);
                    }
                    case 7 -> {
                        Report.generateDailySalesReport(scanner);
                        pauseToReturn(scanner);
                    }
                    case 8 -> {
                        Report.generateUserReport(userManager);
                        pauseToReturn(scanner);
                    }
                    case 9 -> {
                        System.out.println("Logging out...");
                        isRunning = false;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } else {
                switch (option) {
                    case 1 -> {
                        user.displayInfo();
                        pauseToReturn(scanner);
                    }
                    case 2 -> {
                        String confirm;
                        while (true) {
                            System.out.print("Are you sure you want to edit profile (y/n): ");
                            confirm = scanner.nextLine().trim().toLowerCase();
                            if (confirm.equals("yes") || confirm.equals("y")) {
                                userManager.editUserProfile(scanner, user);
                                break;
                            } else if (confirm.equals("no") || confirm.equals("n")) {
                                    System.out.println("Cancelled edit profile.");
                                    break;
                                } else {
                                    System.out.println("Invalid input. Please enter 'y/n'.");
                                }
                            pauseToReturn(scanner);
                        }
                    }
                    case 3 -> {
                        ProductApp.setUser(user);
                        ProductApp.main(null);
                    }
                    case 4 ->{
                        Order.viewCartPage();
                    }
                    case 5 -> {
                        OrderHistoryHandler.viewOrderHistory(user);
                        pauseToReturn(scanner);
                    }
                    case 6 -> {
                        Customer customer = Order.getCurrentCustomer();
                        if (customer != null) {
                            customer.getShoppingCart().saveCartToCSV(customer.getUsername());
                        }
                        System.out.println("Logging out...");
                        isRunning = false;
                    }
                    default -> System.out.println("Invalid option.");
                }
            }
        }
    }

    
    public static void pauseToReturn(Scanner scanner) {
        int input;
        do {
            System.out.print("\nEnter 0 to return back: ");
            while (!scanner.hasNextInt()) {
                System.out.print("Invalid input. Enter 0 to return: ");
                scanner.next(); // discard non-integer
            }
            input = scanner.nextInt();
        } while (input != 0);

        scanner.nextLine(); // clear the newline buffer
    }
    
    public static void thankyou() {
        System.out.println("_________          _______  _        _                   _______          ");
        System.out.println("\\__   __/|\\     /|(  ___  )( (    /|| \\    /\\  |\\     /|(  ___  )|\\     /|");
        System.out.println("   ) (   | )   ( || (   ) ||  \\  ( ||  \\  / /  ( \\   / )| (   ) || )   ( |");
        System.out.println("   | |   | (___) || (___) ||   \\ | ||  (_/ /    \\ (_) / | |   | || |   | |");
        System.out.println("   | |   |  ___  ||  ___  || (\\ \\) ||   _ (      \\   /  | |   | || |   | |");
        System.out.println("   | |   | (   ) || (   ) || | \\   ||  ( \\ \\      ) (   | |   | || |   | |");
        System.out.println("   | |   | )   ( || )   ( || )  \\  ||  /  \\ \\     | |   | (___) || (___) |");
        System.out.println("   )_(   |/     \\||/     \\\\|/    )_)|_/    \\/     \\_/   (_______)(_______)");
        System.out.println("                                                                           ");
    }

}
