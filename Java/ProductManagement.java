package All;

import java.util.*;
import java.util.stream.Collectors;

public class ProductManagement {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showProductManagement() {
        while (true) {
            MenuManager.main(null);
            System.out.println("\n======= Product Management =======");
            System.out.println("\t1. View Products");
            System.out.println("\t2. Add Product");
            System.out.println("\t3. Edit Product");
            System.out.println("\t4. Delete Product");
            System.out.println("\t5. View Trash & Restore Product");
            System.out.println("\t6. Search & Filter Products");
            System.out.println("\t7. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {                
                case "1":
                    ProductApp.showAllProducts();
                    MenuManager.pauseToReturn(scanner);
                    break;
                case "2":
                    addProduct();
                    MenuManager.pauseToReturn(scanner);
                    break;
                case "3":
                    editProduct();
                    MenuManager.pauseToReturn(scanner);
                    break;
                case "4":
                    deleteProduct();
                    MenuManager.pauseToReturn(scanner);
                    break;
                case "5":
                    restoreDeletedProduct();
                    MenuManager.pauseToReturn(scanner);
                    break;  
                case "6":
                    searchProduct();
                    break;
                case "7":
                    System.out.println("Returning to main menu...");
                    return; 
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
  public static String generateNextId() {
    List<Product> allProducts = ProductStore.getAllProducts();
    int maxId = 0;
    for (Product p : allProducts) {
        try {
            String numberPart = p.getId().replaceAll("\\D+", "");
            int idNum = Integer.parseInt(numberPart);
            if (idNum > maxId) maxId = idNum;
        } catch (Exception ignored) {}
    }
    return String.format("H%03d", maxId + 1);
}
  
    public static void addProduct() {
        System.out.println("\n=== Add New Product ===");

    String id = generateNextId();
    System.out.println("Product ID: " + id);

    // Name
// Name (must not be duplicate)
    String name;
    while (true) {
        System.out.print("Name: ");
        name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
        } else if (ProductStore.isProductNameUsed(name)) {
            System.out.println("This product name already exists. Please choose another name.");
        } else {
            break;
        }
    }

    // Category
    String category;
    while (true) {
        System.out.print("Category(Wireless/Wired): ");
        category = scanner.nextLine().trim();
        if (category.isEmpty()) {
            System.out.println("Category cannot be empty.");
        } else if (!(category.equalsIgnoreCase("Wireless") || category.equalsIgnoreCase("Wired"))) {
            System.out.println("Invalid category. Only 'Wireless' or 'Wired' is allowed.");
        } else {
            category = category.substring(0, 1).toUpperCase() + category.substring(1).toLowerCase();
            break;  
        }
    }

    // Price
    double price = 0;
    while (true) {
        System.out.print("Price: ");
        String input = scanner.nextLine().trim();
        try {
            price = Double.parseDouble(input);
            if (price < 0) throw new NumberFormatException();
            break;
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Enter a positive number.");
        }
    }

    // Description
    String description;
    while (true) {
        System.out.print("Description: ");
        description = scanner.nextLine().trim();
        if (description.isEmpty()) {
            System.out.println("Description cannot be empty.");
        } else {
            break;
        }
    }

    // Brand
    String brand;
    while (true) {
        System.out.print("Brand: ");
        brand = scanner.nextLine().trim();
        if (brand.isEmpty()) {
            System.out.println("Brand cannot be empty.");
        } else {
            break;
        }
    }

    // Rating
    double rating = 0;
    while (true) {
        System.out.print("Rating (0.0 to 5.0): ");
        String input = scanner.nextLine().trim();
        try {
            rating = Double.parseDouble(input);
            if (rating < 0 || rating > 5) throw new NumberFormatException();
            break;
        } catch (NumberFormatException e) {
            System.out.println("Invalid rating. Enter a number between 0.0 and 5.0.");
        }
    }

    // Review Count
    int reviewCount = 0;
    while (true) {
        System.out.print("Review Count: ");
        String input = scanner.nextLine().trim();
        try {
            reviewCount = Integer.parseInt(input);
            if (reviewCount < 0) throw new NumberFormatException();
            break;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Enter a positive integer.");
        }
    }

    // Stock
    int stock = 0;
    while (true) {
        System.out.print("Stock Quantity: ");
        String input = scanner.nextLine().trim();
        try {
            stock = Integer.parseInt(input);
            if (stock < 0) throw new NumberFormatException();
            break;
        } catch (NumberFormatException e) {
            System.out.println("Invalid stock quantity. Enter a positive integer.");
        }
    }

    // Discounted Price
    double discountedPrice = 0;
    while (true) {
        System.out.print("Discounted Price: ");
        String input = scanner.nextLine().trim();
        try {
            discountedPrice = Double.parseDouble(input);
            if (discountedPrice < 0 || discountedPrice > price) {
                System.out.println("Discounted price must be >= 0 and <= original price.");
            } else {
                break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid discounted price.");
        }
    }
    
    System.out.print("Battery life (in hours, e.g., 30): ");
    String battery = scanner.nextLine().trim() + "h";

    System.out.print("Weight (in grams, e.g., 250): ");
    String weight = scanner.nextLine().trim() + "g";


    Map<String, String> specifications = new HashMap<>();
    specifications.put("Battery", battery);
    specifications.put("Weight", weight);

    // Shipping Info
    String shippingInfo;
    while (true) {
        System.out.print("Shipping Info: ");
        shippingInfo = scanner.nextLine().trim();
        if (shippingInfo.isEmpty()) {
            System.out.println("Shipping Info cannot be empty.");
        } else {
            break;
        }
    }

    // Warranty
    String warranty;
    while (true) {
        System.out.print("Warranty: ");
        warranty = scanner.nextLine().trim();
        if (warranty.isEmpty()) {
            System.out.println("Warranty cannot be empty.");
        } else {
            break;
        }
    }

    // Tags
    List<String> tags = new ArrayList<>();
    System.out.print("Tags (comma-separated): ");
    String tagLine = scanner.nextLine().trim();
    if (!tagLine.isEmpty()) {
        tags = Arrays.stream(tagLine.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

while (true) {
    System.out.print("Are you sure to add this product? (y)es or (n)o: ");
    String confirm = scanner.nextLine().trim().toLowerCase(); // handles Y, y, YES, etc.

    if (confirm.equals("y") || confirm.equals("yes")) {
        Product product;
    if (category.equalsIgnoreCase("Wireless")) {
        product = new WirelessHeadphones(id, name, category, price, description, brand,
                rating, reviewCount, stock, discountedPrice, specifications,
                shippingInfo, warranty, tags);
    } else {
        product = new WiredHeadphones(id, name, category, price, description, brand,
                rating, reviewCount, stock, discountedPrice, specifications,
                shippingInfo, warranty, tags);
    }    
        
        ProductStore.addProduct(product);
        System.out.println("Product added successfully.");
        break;
    } else if (confirm.equals("n") || confirm.equals("no")) {
        System.out.println("Product addition cancelled.");
        break;
    } else {
        System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
    }
}
    showProductManagement();
}


    public static void editProduct() {
        List<Product> productList = ProductStore.getAllProducts(); 
        System.out.println("\n=== Edit Product ===");
        System.out.print("Enter the product ID to edit: ");
        String id = scanner.nextLine().trim();
        Product existingProduct = ProductStore.getById(id);
        if (existingProduct == null) {
            System.out.println("Product not found.");
            return;
        }
        System.out.println("Editing product: " + existingProduct.getName());
        boolean updated = false;

        // Name
        System.out.println("Current Name: " + existingProduct.getName());
        System.out.print("New Name (leave empty to keep current): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) {
            boolean duplicate = productList.stream()
                .anyMatch(p -> !p.getId().equals(existingProduct.getId()) && p.getName().equalsIgnoreCase(name));
            if (duplicate) {
                System.out.println("This name already exists. Name not updated.");
            } else {
                existingProduct.setName(name);
                System.out.println("Name updated successfully.");
                updated = true;
            }
        } else {
            System.out.println("No change to Name.");
        }

        // Category
        System.out.println("Current Category: " + existingProduct.getCategory());

        while (true) {
            System.out.print("New Category (leave empty to keep current): ");
            String category = scanner.nextLine().trim();

            if (category.isEmpty()) {
                System.out.println("No change to Category.");
                break;
            } else if (category.equalsIgnoreCase("Wireless") || category.equalsIgnoreCase("Wired")) {
                System.out.println("Category updated successfully.");
                updated = true;
                break;
            } else {
                System.out.println("Invalid category. Only 'Wireless' or 'Wired' allowed.");
            }
        }

        // Price
        System.out.println("Current Price: " + existingProduct.getPrice());
        System.out.print("New Price (leave empty to keep current): ");
        String priceInput = scanner.nextLine().trim();
        if (!priceInput.isEmpty()) {
            try {
                double price = Double.parseDouble(priceInput);
                if (price < 0) throw new NumberFormatException();
                existingProduct.setPrice(price);
                System.out.println("Price updated successfully.");
                updated = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Enter a positive number.");
            }
        } else {
            System.out.println("No change to Price.");
        }

        // Description
        System.out.println("Current Description: " + existingProduct.getDescription());
        System.out.print("New Description (leave empty to keep current): ");
        String description = scanner.nextLine().trim();
        if (!description.isEmpty()) {
            existingProduct.setDescription(description);
            System.out.println("Description updated successfully.");
            updated = true;
        } else {
            System.out.println("No change to Description.");
        }

        // Brand
        System.out.println("Current Brand: " + existingProduct.getBrand());
        System.out.print("New Brand (leave empty to keep current): ");
        String brand = scanner.nextLine().trim();
        if (!brand.isEmpty()) {
            existingProduct.setBrand(brand);
            System.out.println("Brand updated successfully.");
            updated = true;
        } else {
            System.out.println("No change to Brand.");
        }

        // Rating
        System.out.println("Current Rating: " + existingProduct.getRating());
        System.out.print("New Rating (leave empty to keep current): ");
        String ratingInput = scanner.nextLine().trim();
        if (!ratingInput.isEmpty()) {
            try {
                double rating = Double.parseDouble(ratingInput);
                if (rating < 0 || rating > 5) throw new NumberFormatException();
                existingProduct.setRating(rating);
                System.out.println("Rating updated successfully.");
                updated = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid rating. Enter a number between 0.0 and 5.0.");
            }
        } else {
            System.out.println("No change to Rating.");
        }

        // Review Count
        System.out.println("Current Review Count: " + existingProduct.getReviewCount());
        System.out.print("New Review Count (leave empty to keep current): ");
        String reviewCountInput = scanner.nextLine().trim();
        if (!reviewCountInput.isEmpty()) {
            try {
                int reviewCount = Integer.parseInt(reviewCountInput);
                if (reviewCount < 0) throw new NumberFormatException();
                existingProduct.setReviewCount(reviewCount);
                System.out.println("Review Count updated successfully.");
                updated = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid review count. Enter a positive integer.");
            }
        } else {
            System.out.println("No change to Review Count.");
        }

        // Stock Quantity
        System.out.println("Current Stock Quantity: " + existingProduct.getStock());
        System.out.print("New Stock Quantity (leave empty to keep current): ");
        String stockInput = scanner.nextLine().trim();
        if (!stockInput.isEmpty()) {
            try {
                int stock = Integer.parseInt(stockInput);
                if (stock < 0) throw new NumberFormatException();
                existingProduct.setStock(stock);
                System.out.println("Stock Quantity updated successfully.");
                updated = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid stock quantity. Enter a positive integer.");
            }
        } else {
            System.out.println("No change to Stock Quantity.");
        }

        // Discounted Price
        System.out.println("Current Discounted Price: " + existingProduct.getDiscountedPrice());
        System.out.print("New Discounted Price (leave empty to keep current): ");
        String discountedPriceInput = scanner.nextLine().trim();
        if (!discountedPriceInput.isEmpty()) {
            try {
                double discountedPrice = Double.parseDouble(discountedPriceInput);
                if (discountedPrice < 0 || discountedPrice > existingProduct.getPrice()) {
                    System.out.println("Discounted price must be >= 0 and <= original price.");
                } else {
                    existingProduct.setDiscountedPrice(discountedPrice);
                    System.out.println("Discounted Price updated successfully.");
                    updated = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid discounted price.");
            }
        } else {
            System.out.println("No change to Discounted Price.");
        }

        // Specifications
        System.out.println("Current Specifications:");
        existingProduct.getSpecifications().forEach((k, v) -> System.out.println("- " + k + " = " + v));
        System.out.println("Enter new specifications (key=value) or leave empty to keep current:");
        Map<String, String> specs = existingProduct.getSpecifications();
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            String[] parts = line.split("=", 2);
            if (parts.length == 2 && !parts[0].trim().isEmpty()) {
                specs.put(parts[0].trim(), parts[1].trim());
                System.out.println("Specification updated successfully.");
                updated = true;
            } else {
                System.out.println("Invalid format. Please use key=value.");
            }
        }

        // Shipping Info
        System.out.print("New Shipping Info (leave empty to keep current): ");
        String shippingInfo = scanner.nextLine().trim();
        if (!shippingInfo.isEmpty()) {
            existingProduct.setShippingInfo(shippingInfo);
            System.out.println("Shipping Info updated successfully.");
            updated = true;
        } else {
            System.out.println("No change to Shipping Info.");
        }

        // Warranty
        System.out.print("New Warranty (leave empty to keep current): ");
        String warranty = scanner.nextLine().trim();
        if (!warranty.isEmpty()) {
            existingProduct.setWarranty(warranty);
            System.out.println("Warranty updated successfully.");
            updated = true;
        } else {
            System.out.println("No change to Warranty.");
        }

        // Tags
        System.out.println("Current Tags: " + String.join(", ", existingProduct.getTags()));
        System.out.print("New Tags (comma-separated, leave empty to keep current): ");
        String tagsLine = scanner.nextLine().trim();
        if (!tagsLine.isEmpty()) {
            List<String> tags = Arrays.stream(tagsLine.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            existingProduct.setTags(tags);
            System.out.println("Tags updated successfully.");
            updated = true;
        } else {
            System.out.println("No change to Tags.");
        }

        // Display full product info
        System.out.println("\n=== Product Info ===");
        System.out.println("Product ID: " + existingProduct.getId());
        System.out.println("Name: " + existingProduct.getName());
        System.out.println("Category: " + existingProduct.getCategory());
        System.out.println("Price: " + existingProduct.getPrice());
        System.out.println("Description: " + existingProduct.getDescription());
        System.out.println("Brand: " + existingProduct.getBrand());
        System.out.println("Rating: " + existingProduct.getRating());
        System.out.println("Review Count: " + existingProduct.getReviewCount());
        System.out.println("Stock Quantity: " + existingProduct.getStock());
        System.out.println("Discounted Price: " + existingProduct.getDiscountedPrice());
        System.out.println("Specifications: " + existingProduct.getSpecifications());
        System.out.println("Shipping Info: " + existingProduct.getShippingInfo());
        System.out.println("Warranty: " + existingProduct.getWarranty());
        System.out.println("Tags: " + String.join(", ", existingProduct.getTags()));

        // Confirmation
        if (updated) {
            while (true) {
                System.out.print("Are you sure to update this product? (y)es or (n)o: ");
                String confirm = scanner.nextLine().trim().toLowerCase();
                if (confirm.equals("y") || confirm.equals("yes")) {
                    System.out.println("Product updated successfully.");
                    showProductManagement();
                    break;
                } else if (confirm.equals("n") || confirm.equals("no")) {
                    System.out.println("Product update cancelled.");
                    showProductManagement();
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }
            }
        } else {
            System.out.println("No updates were made.");
        }
    }


public static void deleteProduct() {
    System.out.println("\n=== Delete Product ===");

    while (true) {
        System.out.print("Enter the product ID to delete: ");
        String id = scanner.nextLine().trim();
        Product product = ProductStore.getById(id);

        if (product == null) {
            System.out.println("Product not found.");
            System.out.print("Do you want to try again? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (!choice.equals("y") && !choice.equals("yes")) {
                return;
            }
        } else {
            System.out.println("\n--- Product Details ---");
            System.out.println("ID: " + product.getId());
            System.out.println("Name: " + product.getName());
            System.out.println("Category: " + product.getCategory());
            System.out.printf("Price: %.2f\n", product.getPrice());
            System.out.println("Description: " + product.getDescription());
            System.out.println("Brand: " + product.getBrand());
            System.out.printf("Rating: %.1f\n", product.getRating());
            System.out.println("Review Count: " + product.getReviewCount());
            System.out.println("Stock: " + product.getStock());
            System.out.printf("Discounted Price: %.2f\n", product.getDiscountedPrice());
            System.out.println("Shipping Info: " + product.getShippingInfo());
            System.out.println("Warranty: " + product.getWarranty());
            System.out.println("Tags: " + String.join(", ", product.getTags()));
            System.out.println("Specifications:");
            product.getSpecifications().forEach((k, v) -> System.out.println("  - " + k + ": " + v));

            System.out.print("Are you sure to delete this product? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("y") || confirm.equals("yes")) {
                ProductStore.removeProductById(id);
                System.out.println("It will be permanently deleted after 30 days.");
            } else {
                System.out.println("Deletion cancelled.");
            }
            return; 
        }
    }
}

public static void restoreDeletedProduct() {
    System.out.println("\n=== Deleted Products ===");
    
    // Get the actual deleted products list
    List<DeletedProduct> trash = ProductStore.getDeletedProducts();
        System.out.println("Number of deleted products: " + trash.size());
   

    // Display deleted products
    for (int i = 0; i < trash.size(); i++) {
        DeletedProduct dp = trash.get(i);
        Product p = dp.getProduct();
        System.out.printf("%d. ID: %s, Name: %s, Deleted At: %s\n",
                         i+1, p.getId(), p.getName(), dp.getDeletedAt());
    }

    Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.print("\nEnter product number to restore (0 to cancel): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            if (choice == 0) {
                return;
            }
            
            if (choice > 0 && choice <= trash.size()) {
                DeletedProduct selected = trash.get(choice - 1);
                ProductStore store = new ProductStore();
            if (store.restoreById(selected.getProduct().getId())) {
                System.out.println("Product restored successfully!");
            } else {
                System.out.println("Failed to restore product.");
            }
               
            } else {
                System.out.println("Invalid number. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
}


    
public static void searchProduct() {
    List<Product> searchResults = ProductSearchService.searchProduct();  // Call the search method

    if (!searchResults.isEmpty()) {
        // Display search results
        System.out.println("\nSearch Results:");
        for (Product p : searchResults) {
            System.out.printf("ID: %s, Name: %s, Price: RM%.2f\n", p.getId(), p.getName(), p.getDiscountedPrice());
        }
    } else {
        System.out.println("No products found for your search criteria.");
    }
}
}

