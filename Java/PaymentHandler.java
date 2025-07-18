package All;

import java.util.List;
import java.util.Scanner;

public class PaymentHandler {
    public static Payment selectAndProcessPayment(
            String paymentMethod,
            Scanner scanner, 
            double finalAmount, 
            String name,  
            List<CartItem> purchasedItems,
            String phone,   
            String email,         
            String address,
            User user, 
            String orderID) {
         
        Payment payment = null;
        while (true) {
            System.out.println("\n\t======== Payment Processing ========");
            System.out.println("\nSelect Payment Method:");
            System.out.println("1. Online Banking");
            System.out.println("2. E-Wallet");
            System.out.println("3. Credit Card");
            System.out.println("0. Cancel");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

             payment = switch (choice) {  
            case "1" -> {
                paymentMethod = "Online Banking";  
                yield new OnlineBanking(paymentMethod, name, purchasedItems, phone, email, address, user, orderID);
            }
            case "2" -> {
                paymentMethod = "E-Wallet";  
                yield new EWallet(paymentMethod, name, purchasedItems, phone, email, address, user, orderID);
            }
            case "3" -> {
                paymentMethod = "Credit Card";  
                yield new CreditCard(paymentMethod, name, purchasedItems, phone, email, address, user, orderID);
            }
                case "0" -> {
                    System.out.println("Payment cancelled.");
                    yield null;
                }
                default -> null;
            };
            
                 if (payment == null && !"0".equals(choice)) {
                System.out.println("Invalid choice.");
                continue; 
            }
                 
       
            if (payment != null && payment.process(finalAmount, user)) {
                while (true) {
                System.out.print("\nWould you like to view the invoice? (y/n): ");
                String viewInvoiceChoice = scanner.nextLine().toLowerCase();

                if (viewInvoiceChoice.equals("y") || viewInvoiceChoice.equals("yes")) {
                    Invoice.showInvoice(purchasedItems, payment.paymentRef, paymentMethod, finalAmount, name, phone, email, address, orderID);
                    break;
                } else if (viewInvoiceChoice.equals("n") || viewInvoiceChoice.equals("no")) {
                    System.out.println("Going back to the main menu...");
                    break; 
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }
            }

                return payment;
            } else if (payment != null) {
                System.out.println("Payment cancelled. Please try again.");
            } else {
                return null; 
            }
        }
    }
}
