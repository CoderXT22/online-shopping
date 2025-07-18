package All;

import java.util.*;

public class ProductSearchService {
    private static final Scanner scanner = new Scanner(System.in);

    public static List<Product> searchProduct() {
        List<Product> results = new ArrayList<>();
        while (true) {
            MenuManager.main(null);
            System.out.println("\n\tSearch Products");
            System.out.println("\t1. Search by Product Name");
            System.out.println("\t2. Search by Product ID");
            System.out.println("\t3. Search by Category");
            System.out.println("\t4. Sort by Rating");
            System.out.println("\t5. Sort by Price (Low to High)");
            System.out.println("\t6. Return Back");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> results = search("name", "Enter product name keyword: ");
                case "2" -> results = searchByProductID();
               case "3" -> results = searchByCategory();
                case "4" -> results = sortByRating();
                case "5" -> results = sortByPrice();
                case "6" -> {return results;}
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
            }

            if (!results.isEmpty()) {
                showSearchResults(results);
            }
        }
    }

    private static List<Product> search(String type, String prompt) {
        System.out.print(prompt);
        String keyword = scanner.nextLine().toLowerCase();
        return ProductStore.searchProducts(keyword, 0, Double.MAX_VALUE, type, "rating");
    }

    private static List<Product> searchByProductID() {
        while (true) {
            System.out.print("Enter product ID: ");
            String id = scanner.nextLine().trim();
             List<Product> results = ProductStore.searchProducts(id, 0, Double.MAX_VALUE, "id", "rating");

            if (results.isEmpty()) {
                System.out.println("No products found.");
                if (!retryOrReturn()) {
                    return new ArrayList<>(); // Return empty list to end search
                }
            } else {
                ProductApp.showProductDetail(results.get(0));  // Show first result
                return results;
            }
        }
    }

    private static boolean retryOrReturn() {
        while (true) {
            System.out.print("Would you like to try again? (Y/N): ");
            String retry = scanner.nextLine().trim().toLowerCase();
            if (retry.equals("n")) {
                return false; // Return to previous menu
            } else if (retry.equals("y")) {
                return true; // Retry searching
            } else {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        }
    }

    private static List<Product> sortByRating() {
        List<Product> results = new ArrayList<>(ProductStore.getAllProducts());
        results.sort(Comparator.comparingDouble(Product::getRating).reversed());
        return results;
    }

    private static List<Product> sortByPrice() {
        List<Product> results = new ArrayList<>(ProductStore.getAllProducts());
        results.sort(Comparator.comparingDouble(Product::getDiscountedPrice));
        return results;
    }

    private static void showSearchResults(List<Product> products) {
        if (products == null || products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        while (true) {
            System.out.println("\nSearch Results:");
            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                System.out.printf("%d. [%s] %s - RM%.2f\n", i + 1, p.getId(), p.getName(), p.getDiscountedPrice());
            }

            System.out.print("Select a product to view details (or 0 to return): ");
            String input = scanner.nextLine().trim();

            try {
                int index = Integer.parseInt(input);
                if (index == 0) {
                    return;
                } else if (index >= 1 && index <= products.size()) {
                    ProductApp.showProductDetail(products.get(index - 1));
                    return;
                } else {
                    System.out.println("Invalid selection. Please choose between 1 and " + products.size() + ", or 0 to return.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }


    private static List<Product> searchByCategory() {
         while (true) {
        System.out.print("Enter category (wireless/wired): ");
        String category = scanner.nextLine().trim().toLowerCase();

        if (category.isEmpty()) {
            return new ArrayList<>();
        }

        if (!category.equals("wireless") && !category.equals("wired")) {
            System.out.println("Invalid input. Please enter 'wireless' or 'wired'.");
            continue;
        }

        List<Product> results = ProductStore.searchProducts(category, 0, Double.MAX_VALUE, "category", "rating");

        if (results.isEmpty()) {
            System.out.println("No products found in category: " + category);
            if (!retryOrReturn()) {
                return new ArrayList<>();
            }
        } else {
            return results;
         }
        }
    }
}
