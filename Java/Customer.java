package All;
import java.util.Scanner;


class Customer extends User {
    private ShoppingCart shoppingCart;

    public Customer(int userId, String username, String email, String password) {
       super(userId, username, email, password, "customer");
       this.shoppingCart = new ShoppingCart();  // Initialize the shopping cart
    }
    
    
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void displayInfo() {
        System.out.println("Customer: " + username);
        System.out.println("Email: " + email);
    }
    
    public static void register(UserManager userManager) {
        Scanner scanner = new Scanner(System.in);
    
        String username, email, password, confirmPassword;
        
        System.out.println("\n\t--- Register ---");
        while (true) {
            System.out.print("Enter username: ");
            username = scanner.nextLine();
            if (userManager.getUser(username) != null) {
                System.out.println("Username already exists. Please choose another.");
            } else if (username.isBlank()) {
                System.out.println("Username cannot be empty.");
            } else {
                break;
            }
        }
    
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
    
            if (!email.contains("@") || !email.contains(".")) {
                System.out.println("Invalid email format. Please enter a valid email.");
            } else {
                break;
            }
        }
        
        while(true) {   
            System.out.print("Enter password (min 8 characters): ");
            password = scanner.nextLine();
    
            if (password.length() < 8) {
                System.out.println("Password must be at least 8 characters long.");
                continue;
            }
    
            System.out.print("Confirm password: ");
            confirmPassword = scanner.nextLine();
    
            if (email.isBlank() || password.isBlank()) {
                System.out.println("Email and password cannot be empty.");
            } else if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");
            } else {
                break;
            }
        }
    
        userManager.registerCustomer(username, email, password);
        System.out.println("Registration successful! You can now log in.");
    }
}
