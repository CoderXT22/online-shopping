package All;
import java.io.*;
import java.io.File;
import java.util.Scanner;
import java.util.Map;
import java.util.TreeMap;

public class Report {
    public static void viewAllOrders() {
        File folder = new File(".");
        File[] files = folder.listFiles((dir, name) -> name.endsWith("_orderHistory.txt"));

        if (files == null || files.length == 0) {
            System.out.println("No order histories found.");
            return;
        }

        System.out.println("\n=== All Order Histories ===\n");

        for (File file : files) {
            System.out.println(">>> Orders for User: " + file.getName().replace("_orderHistory.txt", ""));
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println(); // blank line between users
            } catch (IOException e) {
                System.out.println("Error reading " + file.getName() + ": " + e.getMessage());
            }
        }
    }

    public static void generateMonthlySalesReport() {
        File folder = new File(".");
        File[] files = folder.listFiles((dir, name) -> name.endsWith("_orderHistory.txt"));
        if (files == null || files.length == 0) {
            System.out.println("No order histories found.");
            return;
        }

        Map<String, Double> monthlyTotals = new TreeMap<>();

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                String orderDate = null;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Order Date")) {
                        // Modified to handle your format
                        orderDate = line.split(":")[1].trim().substring(3); // Get MM-YYYY
                    }
                    if (line.startsWith("Total Amount")) {
                        // Modified to handle your format
                        String amountStr = line.split(":")[1].trim().replace("RM", "");
                        double amount = Double.parseDouble(amountStr);
                        monthlyTotals.put(orderDate, monthlyTotals.getOrDefault(orderDate, 0.0) + amount);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error processing " + file.getName());
            }
        }

        System.out.println("\n=== Monthly Sales Report ===\n");
        for (Map.Entry<String, Double> entry : monthlyTotals.entrySet()) {
            System.out.printf("%s: RM%.2f%n", entry.getKey(), entry.getValue());
        }
    }

    public static void generateDailySalesReport(Scanner scanner) {
        System.out.print("Enter date (dd-MM-yyyy): ");
        String targetDate = scanner.nextLine().trim();
        double total = 0.0;
        boolean found = false;

        File[] files = new File(".").listFiles((dir, name) -> name.endsWith("_orderHistory.txt"));
        if (files == null || files.length == 0) {
            System.out.println("No order histories found.");
            return;
        }

        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                String orderDate = null;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Order Date")) {
                        // Modified to handle your format
                        orderDate = line.split(":")[1].trim();
                    }
                    if (line.startsWith("Total Amount") && orderDate != null && orderDate.equals(targetDate)) {
                        // Modified to handle your format
                        String amountStr = line.split(":")[1].trim().replace("RM", "");
                        total += Double.parseDouble(amountStr);
                        found = true;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error processing file");
            }
        }

        System.out.println("\n=== Daily Sales Report ===");
        System.out.println("Date: " + targetDate);
        System.out.printf("Total sales: RM%.2f%n", total);
        System.out.println("---------------------------");
    }


    public static void generateUserReport(UserManager userManager) {
        System.out.println("\n\t\t=== User Report ===\n");
        int customerCount = 0;
        int adminCount = 0;

        for (User user : userManager.getAllUsers()) {
            System.out.println("Username: " + user.getUsername() + ", Email: " + user.getEmail() + ", Role: " + user.getRole());
            if (user.getRole().equalsIgnoreCase("admin")) {
                adminCount++;
            } else {
                customerCount++;
            }
        }

        System.out.println("\nTotal Users: " + (customerCount + adminCount));
        System.out.println("Customers: " + customerCount);
        System.out.println("Admins: " + adminCount);
    }
}
