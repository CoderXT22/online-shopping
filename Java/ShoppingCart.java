package All;

import java.util.*;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class ShoppingCart {
    private List<CartItem> cartItems;
    private double totalCost;

    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
        this.totalCost = 0.0;
    }

    // Add item to cart
public void addItem(CartItem newItem) {
    Product product = newItem.getProduct();
    int desiredQuantity = newItem.getQuantity();

    // Check if stock is sufficient
    if (product.getStock() < desiredQuantity) {
        System.out.println("Sorry, not enough stock for " + product.getName() + ". Available stock: " + product.getStock());
        return;
    }

    // Check if item already exists in the cart
    for (CartItem item : cartItems) {
        if (item.getProduct().getId().equals(product.getId())) {
            item.setQuantity(item.getQuantity() + desiredQuantity);
            updateTotalCost();
            return;
        }
    }

    // If the product doesn't exist in the cart, add it
    cartItems.add(newItem);
    updateTotalCost();
}


    // Interactive remove item from cart
public void removeItem(Scanner scanner) {
    if (cartItems.isEmpty()) {
        System.out.println("Your cart is empty.");
        return;
    }

    while (true) {
        // Display the cart table
        viewCart();

        int index = -1;
        int maxIndex = cartItems.size();

        while (true) {
            System.out.print("Enter item number to remove (1-" + maxIndex + ") or 0 to go back: ");
            String input = scanner.nextLine();

            if (input.equals("0")) return;

            try {
                index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < cartItems.size()) {
                    break; // valid input
                } else {
                    System.out.println("Invalid item number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        cartItems.remove(index);
        updateTotalCost();
        System.out.println("Item removed from cart.\n");

        if (cartItems.isEmpty()) {
            System.out.println("Your cart is now empty.");
            return;
        }
    }
}


// Clear the cart
public void clearCart() {
    cartItems.clear();
    updateTotalCost();
}

// Interactive update item quantity in cart
public void updateItemQuantity(Scanner scanner) {
    if (cartItems.isEmpty()) {
        System.out.println("Your cart is empty.");
        return;
    }

    while (true) {
        // Display the cart
        viewCart();

        int index = -1;
        int maxIndex = cartItems.size();

        // Get user input for item to update
        while (true) {
            System.out.print("Enter item number to update quantity (1-" + maxIndex + ") or 0 to go back: ");
            String input = scanner.nextLine();

            if (input.equals("0")) return;

            try {
                index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < cartItems.size()) {
                    break; // valid input, exit loop
                } else {
                    System.out.println("Invalid item number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        CartItem item = cartItems.get(index);
        Product product = item.getProduct();
        int currentQuantity = item.getQuantity();

        // Prompt for new quantity
        while (true) {
            System.out.print("Enter new quantity: ");
            try {
                int newQty = Integer.parseInt(scanner.nextLine());
                if (newQty > 0 && newQty <= product.getStock()) {
                    item.setQuantity(newQty);
                    updateTotalCost();
                    System.out.println("Quantity updated.\n");
                    break;
                } else {
                    System.out.println("Quantity must be between 1 and " + product.getStock() + " (available stock).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid quantity.");
            }
        }
    }
}



    // Update the total cost based on the current cart items
    private void updateTotalCost() {
        totalCost = cartItems.stream().mapToDouble(CartItem::getSubtotal).sum();
    }

    // Get total cost of cart
    public double getTotalCost() {
        return totalCost;
    }

    // Get shopping cart items
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void viewCart() {
    if (cartItems.isEmpty()) {
        System.out.println("Your cart is empty.");
    } else {
        String line = "----------------------------------------------------------------------------------------";
        String header = "=== Shopping Cart ===";
        int totalWidth = line.length();
        int padding = (totalWidth - header.length()) / 2;
        System.out.printf("\n" + "%" + (padding + header.length()) + "s\n", header);
        System.out.println(line);
        System.out.printf("%-5s %-40s %10s %15s %15s\n", "No.", "Product Name", "Quantity", "Price", "Total");
        System.out.println(line);

        int itemNumber = 1;
        for (CartItem item : cartItems) {
            System.out.printf("%-5d %-40s %10d %15s %15s\n",
                itemNumber++,
                item.getProduct().getName(),
                item.getQuantity(),
                String.format("RM %,.2f", item.getProduct().getDiscountedPrice()),
                String.format("RM %,.2f", item.getSubtotal()));
        }

        System.out.println(line);
        System.out.printf("\nTotal Cost: RM %,.2f\n", totalCost);

    }
}

    public void checkout() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty. Please add some items before checking out.");
            return;
        }
        // Proceed to payment logic (could be a separate method or process)
        System.out.printf("Total Cost: RM%.2f\n", totalCost);
        System.out.println("Proceeding to payment...");
        // Optionally, clear the cart after checkout
        clearCart();
    }

    public void saveCartToCSV(String username) {
    String fileName = username + "_cart.csv";

    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
        writer.println("Product ID,Product Name,Quantity,Unit Price,Subtotal");

        // Debugging: Output each cart item to console before saving
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double unitPrice = product.getDiscountedPrice() > 0 ? product.getDiscountedPrice() : product.getPrice();
            double subtotal = unitPrice * quantity;

            writer.printf("%s,%s,%d,%.2f,%.2f%n",
                product.getId(),
                product.getName(),
                quantity,
                unitPrice,
                subtotal
            );
        }

      
    } catch (IOException e) {
    }
}

    public void loadCartFromCSV(String username) {
        clearCart();  // Step 1: Clear the cart before loading

        String fileName = username + "_cart.csv";
        File file = new File(fileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 5) {
                    continue;
                }

                String productId = parts[0];
                String productName = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                double unitPrice = Double.parseDouble(parts[3]);

                // You need to fetch the product using ID (assume ProductDB or existing method)
                Product product = ProductStore.getById(productId);

                if (product == null) {
                    // Fallback in case product no longer exists in DB
                    product = new Product(productId, productName, "", unitPrice, "", "", 0.0, 0, 0, 0.0, new HashMap<>(), "", "", new ArrayList<>()) {};
                }

                CartItem cartItem = new CartItem(product, quantity);
                addItem(cartItem);
            }

        } catch (IOException e) {
            // No error message displayed to user
        } catch (NumberFormatException e) {
            // No error message displayed to user
        }
    }



    @Override
    public String toString() {
        StringBuilder cartDetails = new StringBuilder("Shopping Cart:\n");
        for (CartItem item : cartItems) {
            cartDetails.append(item).append("\n");
        }
        cartDetails.append("Total Cost: ").append(totalCost);
        return cartDetails.toString();
    }
}
