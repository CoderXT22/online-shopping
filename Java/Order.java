package All;

import java.util.Scanner;

public class Order {
    private static Scanner scanner = new Scanner(System.in);
    private static Customer currentCustomer; // Store the current customer
    private static Payment payment;
    
    public static void viewCartPage() {
        if (currentCustomer == null || currentCustomer.getShoppingCart().getCartItems().isEmpty()) {
            System.out.println("Your cart is empty.");
            return; // Skip further processing if the cart is empty
        }

        while (true) {
            //display cart first
            currentCustomer.getShoppingCart().viewCart();
            
            System.out.println("\nOptions:");
            System.out.println("1. Add more items");
            System.out.println("2. Update Item Quantity");
            System.out.println("3. Remove Item");
            System.out.println("4. Clear Cart");
            System.out.println("5. Proceed to Order");
            System.out.println("0. Return to Menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" ->  ProductApp.showAllProducts();
                case "2" -> currentCustomer.getShoppingCart().updateItemQuantity(scanner);
                case "3" -> currentCustomer.getShoppingCart().removeItem(scanner);
                case "4" -> clearCart();
                case "5" -> {
                    proceedToOrder();
                    return;
                }
                case "0" -> {
                    return; // Go back to main menu
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
    
    
    public static void proceedToOrder() {
    if (currentCustomer == null || currentCustomer.getShoppingCart().getTotalCost() == 0) {
        System.out.println("Your cart is empty. Add items before placing an order.");
        return;
    }

    // Check stock before proceeding with the order
    for (CartItem item : currentCustomer.getShoppingCart().getCartItems()) {
        Product product = item.getProduct();
        
        if (product.getStock() < item.getQuantity()) {
            System.out.println("Sorry! Insufficient stock for " + product.getName() + ". Available stock: " + product.getStock());

            // Ask the user for an action and validate the input
            System.out.println("Do you want to remove this item (1) or update the quantity (2)?");
            String action = scanner.nextLine().trim();

            // Input validation for "remove" or "update" actions
            while (!action.equals("1") && !action.equals("2")) {
                System.out.println("Invalid input. Please enter 1 to remove or 2 to update.");
                action = scanner.nextLine().trim();
            }

            if (action.equals("1")) {
                // Remove the item if the user chooses to remove
                currentCustomer.getShoppingCart().getCartItems().remove(item);
                System.out.println("Item removed from the cart.");
            } else if (action.equals("2")) {
                // Handle update action safely
                boolean validQuantity = false;
                while (!validQuantity) {
                    System.out.println("Please enter the new quantity (1 to " + product.getStock() + "):");
                    String input = scanner.nextLine(); // use scanner declared at the top

                    try {
                        int updatedQuantity = Integer.parseInt(input);
                        if (updatedQuantity > 0 && updatedQuantity <= product.getStock()) {
                            item.setQuantity(updatedQuantity);
                            validQuantity = true;
                            System.out.println("Quantity updated to " + updatedQuantity);
                        } else {
                            System.out.println("Invalid quantity. Please enter a number between 1 and " + product.getStock() + ".");
                        }
                    } catch (NumberFormatException e) {
                            System.out.println("Please enter a valid number.");
                    }
                }
            }

        }
    }
    String[] info = getCustomerDetailsAndPrint();
    String name = info[0], address = info[1], phone = info[2], email = info[3];

    CheckoutStrategy strategy = chooseCheckoutStrategy();
        if (strategy == null) {
            viewCartPage();  // Return to cart if user cancels
            return;
        }
    double cartTotal = currentCustomer.getShoppingCart().getTotalCost();
    double finalAmount = strategy.calculateTotal(cartTotal);

    System.out.println("\nOrder Summary:");
    currentCustomer.getShoppingCart().viewCart();
    System.out.printf("Delivery Fee: RM%.2f\n", strategy.getDeliveryFee());
    System.out.printf("Total Amount Payable (after delivery/discount if any): RM%.2f\n", finalAmount);

    if (confirmOrder()) {
        processPayment(name, phone, email, address, finalAmount);
    }
}


public static void DirectOrder(Product p, int qty) {
    if (currentCustomer == null) {
        System.out.println("You must be logged in to place an order.");
        return;
    }

    if (p.getStock() < qty) {
        System.out.println("Sorry! Insufficient stock for " + p.getName() + ". Available stock: " + p.getStock());
        return;
    }

    String[] info = getCustomerDetailsAndPrint();
    String name = info[0], address = info[1], phone = info[2], email = info[3];
  
    CheckoutStrategy strategy = chooseCheckoutStrategy();

    double itemTotal = p.getDiscountedPrice() * qty;
    double finalAmount = strategy.calculateTotal(itemTotal);

    System.out.println("\nOrder Summary:");
    System.out.println(qty + " x " + p.getName() + " @ RM" + p.getDiscountedPrice() + " = RM" + itemTotal);
    System.out.printf("Delivery Fee: RM%.2f\n", strategy.getDeliveryFee());
    System.out.printf("Total Amount Payable (after delivery/discount if any): RM%.2f\n", finalAmount);

    if (confirmOrder()) {
        // Reduce stock
        p.setStock(p.getStock() - qty);
        processPayment(name, phone, email, address, finalAmount);
    } else {
        System.out.println("Order not placed.");
    }
}


    private static void clearCart() {
        currentCustomer.getShoppingCart().clearCart();
        System.out.println("Cart has been cleared!");
    }
    
    public static String[] getCustomerDetailsAndPrint() {
    String name = PromptHelper.promptValidName();
    String address = PromptHelper.promptValidAddress();
    String phone = PromptHelper.promptValidPhone();
    String email = PromptHelper.promptValidEmail();

    System.out.println("\n========Order Details:========");
    System.out.println("Name: " + name);
    System.out.println("Address: " + address);
    System.out.println("Phone: " + phone);
    System.out.println("Email: " + email);
    System.out.println("--------------------------------");

    return new String[]{name, address, phone, email};
}

    
    private static CheckoutStrategy chooseCheckoutStrategy() {
        while (true) {
            System.out.println("\nCheckout Options:");
            System.out.println("1. Proceed to Checkout");
            System.out.println("2. Proceed to Checkout with Discount Code");
            System.out.println("3. Cancel and Return to Cart");
            System.out.print("Please select an option: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                return new StandardCheckout();
            } else if (choice.equals("2")) {
                return new DiscountCheckout();
            } else if (choice.equals("3")) {
                System.out.println("Checkout cancelled. Returning to cart...");
                return null;
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }


    private static boolean confirmOrder() {
        while (true) {
            System.out.print("Confirm order and proceed to payment? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("yes") || confirm.equals("y")) return true;
            if (confirm.equals("no") || confirm.equals("n")) return false;
            System.out.println("Invalid input. Please enter y/n");
        }
    }
    
    private static void processPayment(String name, String phone, String email, String address, double finalAmount) {
        String orderID = "ORD" + System.currentTimeMillis();
        String paymentMethod = null;
        payment = PaymentHandler.selectAndProcessPayment(paymentMethod, scanner, finalAmount, name,
                    currentCustomer.getShoppingCart().getCartItems(), phone, email, address, currentCustomer, orderID);

        if (payment != null) {
            String username = currentCustomer.getUsername();
            System.out.println("Order placed and payment successful! Thank you, " + username + ".\n");
            currentCustomer.getShoppingCart().clearCart();
        } else {
            System.out.print("Press Enter go to Product Dashboard");
            scanner.nextLine();
            viewCartPage();
        }
    }

    // Method to set the current customer (could be done after login)
    public static void setCurrentCustomer(Customer customer) {
        currentCustomer = customer;
    }
    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }
    
}
