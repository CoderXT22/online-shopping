package All;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Invoice {
    public static void showInvoice(List<CartItem> cartItems, String paymentRef, String paymentMethod, double totalPrice, String name, String phone, String email, String address, String orderID) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate orderDate = LocalDate.now();
        LocalDate estimatedDelivery = orderDate.plusDays(3);
        
        System.out.println("\n\n\n=========================== Invoice =================================\n");
        System.out.println("Order ID                : " + orderID);
        System.out.println("Order Date              : " + orderDate.format(formatter));
        System.out.println("Payment Method          : " + paymentMethod);
        System.out.println("Transaction ID          : " + paymentRef);
        System.out.printf("Total Price             : RM%.2f\n", totalPrice);
        System.out.println("Email                   : " + email); 

        // Show cart items in the invoice
        System.out.println("\n------------------------ Purchased Items ---------------------------");
        for (CartItem item : cartItems) {
            System.out.printf("%s | Unit Price : RM%.2f | Quantity: %d | Total: RM%.2f\n",
                    item.getProduct().getName(),
                    item.getUnitPrice(),
                    item.getQuantity(),
                    item.getSubtotal());
        }

        System.out.println("\n--------------------- Delivery  Information ------------------------");
        System.out.println("Name                    : " + name); 
        System.out.println("Phone Number            : " + phone);   
        System.out.println("Address                 : " + address); 
        System.out.println("Estimated Delivery Date : " + estimatedDelivery.format(formatter));
 
        System.out.println("======================================================================\n");
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Press Enter go to Product Dashboard");
        sc.nextLine();
    }
}
