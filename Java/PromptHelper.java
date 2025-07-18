
package All;

import java.util.Scanner;

public class PromptHelper {
    private static final Scanner scanner = new Scanner(System.in);

    // Full name: only letters and spaces allowed
    public static String promptValidName() {
        while (true) {
            System.out.print("Enter your full name: ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty() && name.matches("[a-zA-Z ]+")) return name;
            System.out.println("Name must contain only letters and spaces, and cannot be empty.");
        }
    }

    // Address: user inputs full address (not empty), then postcode (must be 5 digits)
    public static String promptValidAddress() {
        String address;
        while (true) {
            System.out.print("Enter your delivery address: ");
            address = scanner.nextLine().trim();
            if (!address.isEmpty()) break;
            System.out.println("Address cannot be empty.");
        }

        while (true) {
            System.out.print("Enter 5-digit postcode: ");
            String postcode = scanner.nextLine().trim();
            if (postcode.matches("\\d{5}")) return address + ", " + postcode;
            System.out.println("Invalid postcode. Must be exactly 5 digits.");
        }
    }

    // Phone: must be 10 or 11 digits
    public static String promptValidPhone() {
        while (true) {
            System.out.print("Enter phone number: ");
            String phone = scanner.nextLine().trim();
            if (phone.matches("\\d{10,11}")) return phone;
            System.out.println("Phone number must be 10 or 11 digits.");
        }
    }

    // Email: standard format
    public static String promptValidEmail() {
        while (true) {
            System.out.print("Enter email address: ");
            String email = scanner.nextLine().trim();
            if (email.matches("^[\\w.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) return email;
            System.out.println("Invalid email format. Example: user@example.com");
        }
    }
    
}
