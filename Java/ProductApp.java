package All;
import java.util.*;
import All.User;

public class ProductApp {
    private static Scanner scanner = new Scanner(System.in);
    private static User user;
    private static Customer currentCustomer;
    
    public static void setUser(User u) {
    if (u instanceof Customer customer) {
        currentCustomer = customer;
        if (customer.getShoppingCart() == null) {
            customer.setShoppingCart(new ShoppingCart());
        }
        customer.getShoppingCart().loadCartFromCSV(customer.getUsername());
        Order.setCurrentCustomer(currentCustomer); 
    } else {
        currentCustomer = null;
    }
    user = u;
}

    
    public static void main(String[] args) {

        while (true) {
            MenuManager.main(null);
            System.out.println("\t1. View All Products");
            System.out.println("\t2. Search Product");
            System.out.println("\t3. View Cart");
            System.out.println("\t4. Back to Main Menu");

            System.out.print("Enter your choice (1-4): ");
            
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> showAllProducts();
                case "2" -> searchProduct(user);
                case "3" -> Order.viewCartPage();
                case "4" -> {
                    System.out.println("Thank you for visiting!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
                }
            }  
        }
public static void showAllProducts() {
    List<Product> products = ProductStore.getAllProducts();

    while (true) {
        // Display product list once at the top of the loop
        System.out.println("\nAvailable Headphones:");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            System.out.printf("%d. [%s] %s\n", i + 1, p.getId(), p.getName());
        }
        System.out.println("0. Return Back");

        boolean validInput = false;
        while (!validInput) {
            System.out.print("Select a product to view details: ");
            String input = scanner.nextLine();

            if (input.equals("0")) {
                if (user != null && user.getRole().equalsIgnoreCase("admin")) {
                    ProductManagement.showProductManagement();  // Admin menu
                }
                return; 
            }

            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < products.size()) {
                    validInput = true; // Valid, break inner loop
                    showProductDetail(products.get(index)); // Add to cart and return here
                } else {
                    System.out.println("Invalid selection. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }
}



    public static void showProductDetail(Product p) {
    System.out.println("\nProduct Details:");
    System.out.println("Name: " + p.getName());
    System.out.println("Brand: " + p.getBrand());
    System.out.println("Category: " + p.getCategory());
    System.out.println("Description: " + p.getDescription());
    System.out.println("Price: RM" + p.getDiscountedPrice() + " (Original: RM" + p.getPrice() + ")");
    System.out.println("Rating: " + p.getRating() + " (" + p.getReviewCount() + " reviews)");
    System.out.println("Stock: " + p.getStock());
    System.out.println("Shipping Info: " + p.getShippingInfo());
    System.out.println("Warranty: " + p.getWarranty());
    System.out.println("Specifications: " + p.getSpecifications());
    System.out.println("Tags: " + p.getTags());

    if (user.getRole().equals("admin")){
        System.out.println("Press Enter to go back.");
        scanner.nextLine(); 
        return;
    }
    
    
    String choice;
    while (true) {
        System.out.print("\nEnter '1' to Add to Cart or '2' to Order Now: ");
        choice = scanner.nextLine().trim();
        if (choice.equals("1") || choice.equals("2")) {
            break;
        }
        System.out.println("Invalid choice. Please enter 1 or 2.");
    }
    int qty = 0;
    while (true) {
        System.out.print("Enter quantity (1 to " + p.getStock() + "): ");
        String qtyInput = scanner.nextLine().trim();
        try {
            qty = Integer.parseInt(qtyInput);
            if (qty > 0 && qty <= p.getStock()) {
                break;
            } else {
                System.out.println("Invalid quantity. Available stock: " + p.getStock() + ".");
            }
        } catch (NumberFormatException e) {
            
            System.out.println("Invalid quantity. Please enter a number.");
        }
    }
    if (choice.equals("1")) {
        // Add to cart
        CartItem newItem = new CartItem(p, qty);
        currentCustomer.getShoppingCart().addItem(newItem);
        System.out.println("Added to cart.");
    } else if (choice.equals("2")) {
        // Direct order
        String confirm;
        do {
            System.out.print("Confirm order now? (y/n): ");
            confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("y") && !confirm.equals("n")) {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        } while (!confirm.equals("y") && !confirm.equals("n"));

        if (confirm.equals("y")) {
            Order.DirectOrder(p, qty); 
        } else {
            System.out.println("Order cancelled. Item not added to cart.");
        }
    }
}
    
 private static void searchProduct(User user) {
       ProductSearchService.searchProduct();
    }
}

 




 
 
