package All;

import java.io.*;
import java.util.List;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderHistoryHandler {

    public static void saveOrderHistory(User user, List<CartItem> purchasedItems, double totalAmount, String transactionId, String orderID, String name, String address, String phone, String email, String PaymentStatus, String paymentMethod) {
        String fileName = user.getUsername() + "_orderHistory.txt"; // Each user has a separate file

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            writer.newLine();
            writer.write(String.format("Order ID         : %s", orderID));
            writer.newLine();
            writer.write(String.format("Order Date       : %s", LocalDate.now().format(formatter)));
            writer.newLine();
            writer.write(String.format("Payment Method   : %s", paymentMethod));
            writer.newLine();
            writer.write(String.format("Payment Status   : %s", PaymentStatus));
            writer.newLine();

            if (PaymentStatus.equalsIgnoreCase("Paid")) {
                writer.write(String.format("Transaction ID   : %s", transactionId));
                writer.newLine();
                LocalDate estimatedDeliveryDate = LocalDate.now().plusDays(3);
                writer.write(String.format("Est. Delivery    : %s", estimatedDeliveryDate.format(formatter)));
                writer.newLine();
            }
          
            writer.newLine();
            writer.write("Delivery Information:");
            writer.newLine();
            writer.write(String.format("  Name           : %s", name));
            writer.newLine();
            writer.write(String.format("  Address        : %s", address));
            writer.newLine();
            writer.write(String.format("  Phone          : %s", phone));
            writer.newLine();
            writer.write(String.format("  Email          : %s", email));
            writer.newLine();

            writer.newLine();
        writer.write("Purchased Items:");
        writer.newLine();

        if (purchasedItems == null || purchasedItems.isEmpty()) {
            writer.write("  No items purchased.");
            writer.newLine();
        } else {
            for (CartItem item : purchasedItems) {
                writer.write(String.format("  - %-25s RM%.2f x %d = RM%.2f",
                        item.getProduct().getName(),
                        item.getUnitPrice(),
                        item.getQuantity(),
                        item.getUnitPrice() * item.getQuantity()));
                writer.newLine();
            }
        }

            writer.newLine();
            writer.write(String.format("Total Amount     : RM%.2f", totalAmount));
            writer.newLine();
            writer.write("-----------------------------------------------------------");
            writer.newLine();
            writer.newLine();
            
            System.out.println("Order history saved for user: " + user.getUsername());
        } catch (IOException e) {
            System.out.println("Error saving order history: " + e.getMessage());
        }
    }

    public static void viewOrderHistory(User user) {
        String fileName = user.getUsername() + "_orderHistory.txt";
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("No order history found for " + user.getUsername());
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("\n=== Order History for " + user.getUsername() + " ===");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading order history: " + e.getMessage());
        }
    }
}
