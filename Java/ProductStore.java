package All;
import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ProductStore  { 
    private static final List<Product> productList = new ArrayList<>();
    private static final String FILE_NAME = "products.txt";
    private static final List<Product> trashList = new ArrayList<>();
    private static final String trashFilePath = "trash.txt";    
    private static List<DeletedProduct> deletedProducts = new ArrayList<>();
     static {
         loadTrashFromFile();
        productList.add(new WirelessHeadphones(
                "H001", "Sony WH-1000XM5", "Wireless", 1499.00, 
                "Premium noise-cancelling headphones.", "Sony", 4.9, 1200, 20,
                1399.00, Map.of("Battery", "30h", "Weight", "254g"), "Free Shipping", "1 Year",
                List.of("Best Seller", "Noise Cancelling")
        ));

        productList.add(new WirelessHeadphones(
                "H002", "Bose QC45", "Wireless", 1299.00, 
                "Comfortable and rich sound.", "Bose", 4.8, 950, 15,
                1199.00, Map.of("Battery", "24h", "Weight", "240g"), "Free Shipping", "1 Year",
                List.of("Popular", "Comfort Fit")
        ));

        productList.add(new WirelessHeadphones(
                "H003", "JBL Tune 760NC", "Wireless", 699.00, 
                "Affordable noise cancellation.", "JBL", 4.6, 600, 25,
                649.00, Map.of("Battery", "35h", "Weight", "220g"), "Standard", "6 Months",
                List.of("Budget", "ANC")
        ));

        productList.add(new WirelessHeadphones(
                "H004", "Apple AirPods Max", "Wireless", 2399.00, 
                "Luxury over-ear headphones with spatial audio.", "Apple", 4.7, 1100, 10,
                2199.00, Map.of("Battery", "20h", "Weight", "385g"), 
                "Free Shipping", "1 Year", List.of("Premium", "Apple")
        ));

        productList.add(new WirelessHeadphones(
                "H005", "Anker Soundcore Life Q30", "Wireless", 399.00, 
                "Affordable ANC with great battery life.", "Anker", 4.5, 500, 30,
                349.00, Map.of("Battery", "40h", "Weight", "260g"), 
                "Standard", "1 Year", List.of("Budget", "ANC", "Value")
        ));

        productList.add(new WirelessHeadphones(
                "H006", "Beats Studio3", "Wireless", 1299.00, 
                "Stylish and bass-heavy.", "Beats", 4.4, 700, 18,
                1099.00, Map.of("Battery", "22h", "Weight", "260g"), 
                "Free Shipping", "1 Year", List.of("Bass", "Style")
        ));

        productList.add(new WirelessHeadphones(
                "H007", "Sennheiser Momentum 4", "Wireless", 1599.00, 
                "Hi-fi sound with long battery.", "Sennheiser", 4.8, 800, 12,
                1499.00, Map.of("Battery", "60h", "Weight", "293g"), 
                "Free Shipping", "2 Years", List.of("HiFi", "Battery Beast")
        ));

        productList.add(new WirelessHeadphones(
                "H008", "Marshall Major IV", "Wireless", 799.00, 
                "Classic design with long battery.", "Marshall", 4.6, 650, 22,
                749.00, Map.of("Battery", "80h", "Weight", "165g"), 
                "Standard", "1 Year", List.of("Vintage", "Battery King")
        ));

        productList.add(new WirelessHeadphones(
                "H009", "Audio-Technica ATH-M50xBT2", "Wireless", 999.00, 
                "Studio-quality audio on-the-go.", "Audio-Technica", 4.7, 700, 16,
                949.00, Map.of("Battery", "50h", "Weight", "310g"), 
                "Standard", "1 Year", List.of("Studio", "Balanced Sound")
        ));

        productList.add(new WirelessHeadphones(
                "H010", "Skullcandy Crusher Evo", "Wireless", 799.00, 
                "Adjustable bass with haptic feedback.", "Skullcandy", 4.4, 400, 20,
                749.00, Map.of("Battery", "40h", "Weight", "312g"), 
                "Standard", "1 Year", List.of("Bass Boost", "Gaming")
        ));

        productList.add(new WirelessHeadphones(
                "H011", "Shure AONIC 50", "Wireless", 1399.00, 
                "Audiophile ANC headphones.", "Shure", 4.7, 550, 10,
                1299.00, Map.of("Battery", "20h", "Weight", "334g"), 
                "Free Shipping", "2 Years", List.of("Audiophile", "ANC")
        ));

        productList.add(new WirelessHeadphones(
                "H012", "AKG Y600NC", "Wireless", 799.00, 
                "Elegant headphones with ANC.", "AKG", 4.5, 450, 14,
                749.00, Map.of("Battery", "25h", "Weight", "300g"), 
                "Standard", "1 Year", List.of("Elegant", "ANC")
        ));

        // Wired Headphones
        productList.add(new WiredHeadphones(
                "H014", "Logitech G Pro X", "Wired", 699.00, 
                "E-Sports ready with detachable mic.", "Logitech", 4.5, 550, 30,
                649.00, Map.of("Battery","25h","Weight", "320g"), 
                "Standard", "2 Years", List.of("Gaming", "Wired")
        ));

        productList.add(new WiredHeadphones(
                "H015", "Razer Barracuda X", "Wired", 699.00, 
                "Cross-platform gaming headphones.", "Razer", 4.5, 580, 25,
                649.00, Map.of("Battery", "20h", "Weight", "250g"), 
                "Standard", "1 Year", List.of("Gaming", "Versatile")
        ));

        productList.add(new WiredHeadphones(
                "H013", "HyperX Cloud MIX", "Wired", 899.00, 
                "Gaming + mobile hybrid headphones.", "HyperX", 4.6, 600, 17,
                849.00, Map.of("Battery", "20h", "Weight", "260g"), 
                "Standard", "2 Years", List.of("Gaming", "Hybrid")
        )
        );
    }
     public static List<DeletedProduct> getDeletedProducts() {
        return deletedProducts;
    }
     
    public static void addProduct(Product product) {
        productList.add(product);
        saveToFile();
    }
// Check if the product name already exists
    static boolean isProductNameUsed(String name) {
        for (Product p : productList) {
            if (p.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static void saveToFile() {
    try (PrintWriter writer = new PrintWriter(new File(FILE_NAME))) {
        for (Product product : productList) {
            // Create a CSV line from the product's fields (make sure to format correctly)
            StringBuilder sb = new StringBuilder();
            sb.append(product.getId()).append(";")
              .append(product.getName()).append(";")
              .append(product.getCategory()).append(";")
              .append(product.getPrice()).append(";")
              .append(product.getDescription()).append(";")
              .append(product.getBrand()).append(";")
              .append(product.getRating()).append(";")
              .append(product.getReviewCount()).append(";")
              .append(product.getStock()).append(";")
              .append(product.getDiscountedPrice()).append(";")
              .append(mapToString(product.getSpecifications())).append(";")
              .append(product.getShippingInfo()).append(";")
              .append(product.getWarranty()).append(";")
              .append(String.join(",", product.getTags()));  // Tags are stored as comma-separated
            writer.println(sb.toString());
        }
    } catch (IOException e) {
        System.err.println("Error saving product list to file: " + e.getMessage());
    }
}

   
    public static void displayAllProducts() {
        for (Product product : productList) {
            System.out.println(product);
        }
    }
public static void loadFromFile() {
    productList.clear(); 
    File file = new File(FILE_NAME);

    // Check if the file exists
    if (!file.exists()) {
        try {
            file.createNewFile();          
        } catch (IOException e) {
            System.err.println("Failed to create " + FILE_NAME + ": " + e.getMessage());
        }
        return; // Exit early since file is empty or just created
    }

    // Proceed to load products
    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(";", -1);
            if (data.length < 14) {
                System.err.println("Skipping row due to insufficient data: " + line);
                continue;
            }

            String id = data[0];
            String name = data[1];
            String category = data[2];
            double price = parseDoubleOrDefault(data[3], 0.0);
            String description = data[4];
            String brand = data[5];
            double rating = parseDoubleOrDefault(data[6], 0.0);
            int reviewCount = parseIntOrDefault(data[7], 0);
            int stock = parseIntOrDefault(data[8], 0);
            double discountedPrice = parseDoubleOrDefault(data[9], 0.0);
            Map<String, String> specs = stringToMap(data[10]);
            String shippingInfo = data[11];
            String warranty = data[12];
            List<String> tags = List.of(data[13].split(","));

            Product product;
            if (category.equalsIgnoreCase("Wireless")) {
                product = new WirelessHeadphones(id, name, category, price, description,
                        brand, rating, reviewCount, stock, discountedPrice,
                        specs, shippingInfo, warranty, tags);
            } else {
                product = new WiredHeadphones(id, name, category, price, description,
                        brand, rating, reviewCount, stock, discountedPrice,
                        specs, shippingInfo, warranty, tags);
            }

            if (!isProductDeleted(id)) {
                productList.add(product);
            }
        }
    } catch (IOException | NumberFormatException e) {
        System.err.println("Error loading product list: " + e.getMessage());
    }
}

// Helper method to check if the product has been deleted
private static boolean isProductDeleted(String id) {
    // Implement logic to check if the product ID exists in the trash
    try (Scanner scanner = new Scanner(new File(trashFilePath))) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split(";", -1);
            if (data.length > 0 && data[0].equals(id)) {
                return true;
            }
        }
    } catch (IOException e) {
        System.err.println("Error checking deleted products: " + e.getMessage());
    }
    return false;
}

// Helper method to safely parse a double value with a default value
public static double parseDoubleOrDefault(String value, double defaultValue) {
    try {
        return Double.parseDouble(value);
    } catch (NumberFormatException e) {
        return defaultValue;  // Return default value if parsing fails
    }
}

// Helper method to safely parse an integer value with a default value
public static int parseIntOrDefault(String value, int defaultValue) {
    try {
        return Integer.parseInt(value);
    } catch (NumberFormatException e) {
        return defaultValue;  // Return default value if parsing fails
    }
}
public static Map<String, String> stringToMap(String specs) {
    Map<String, String> specsMap = new HashMap<>();
    if (specs != null && !specs.trim().isEmpty()) {
        String[] entries = specs.split(",");
        for (String entry : entries) {
            String[] keyValue = entry.split("=");
            if (keyValue.length == 2) {
                specsMap.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
    }
    return specsMap;
}
    public static List<Product> getAllProducts() {
        return productList;
    }

    public static Product getById(String id) {
        for (Product hp : productList) {
            if (hp.getId().equals(id)) return hp;
        }
        return null;
    }
    
    
    public static List<Product> searchProducts(String keyword, double minPrice, double maxPrice, String filterType, String sortBy) {
    List<Product> filteredProducts = productList.stream()
        .filter(product -> {
            boolean matchesKeyword = false;
            switch (filterType.toLowerCase()) {
                case "name":
                    matchesKeyword = product.getName().toLowerCase().contains(keyword.toLowerCase());
                    break;
                case "id":
                    matchesKeyword = product.getId().toLowerCase().contains(keyword.toLowerCase());
                    break;
                case "category":
                    matchesKeyword = product.getCategory().toLowerCase().contains(keyword.toLowerCase());
                    break;
                default:
                    matchesKeyword = true; 
            }
            return matchesKeyword && product.getDiscountedPrice() >= minPrice && product.getDiscountedPrice() <= maxPrice;
        })
        .collect(Collectors.toList());

    Comparator<Product> comparator;
    switch (sortBy.toLowerCase()) {
        case "price":
            comparator = Comparator.comparingDouble(Product::getDiscountedPrice);
            break;
        case "rating":
            comparator = Comparator.comparingDouble(Product::getRating).reversed();
            break;
        case "name":
            comparator = Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER);
            break;
        default:
            comparator = Comparator.comparing(Product::getId); 
    }

    filteredProducts.sort(comparator);
    return filteredProducts;
}

    public boolean restoreById(String id) {
    Product toRestore = null;
    for (Product p : trashList) {
        if (p.getId().equalsIgnoreCase(id)) {
            toRestore = p;
            break;
        }
    }

    if (toRestore != null) {
        trashList.remove(toRestore);
        productList.add(toRestore);
        saveToFile();
        saveTrashToFile();
        System.out.println("Product restored successfully.");
        return true;
    } else {
        System.out.println("Product not found in trash.");
        return false;
    }
}
public static boolean removeProductById(String id) {
    Product toRemove = getById(id);
    if (toRemove != null) {
        // Debug output
        System.out.println("Moving product to trash: " + toRemove.getName());
        System.out.println("Trash file location: " + new File(trashFilePath).getAbsolutePath());
        
        // Update collections
        productList.remove(toRemove);
        trashList.add(toRemove);
        
        // Save changes
        try {
            saveToFile();
            saveTrashToFile();
            System.out.println("Successfully moved to trash. Trash size: " + trashList.size());
            return true;
        } catch (Exception e) {
            System.err.println("Failed to save changes:");
            e.printStackTrace();
            // Revert changes
            productList.add(toRemove);
            trashList.remove(toRemove);
        }
    }
    System.out.println("Product not found: " + id);
    return false;
}



    public static String mapToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);  // Remove last comma
        }
        return sb.toString();
    }

private static void saveTrashToFile() {
    try (PrintWriter writer = new PrintWriter(new File(trashFilePath))) {
        for (Product product : trashList) {
            StringBuilder sb = new StringBuilder();
            sb.append(product.getId()).append(";")
              .append(product.getName()).append(";")
              .append(product.getCategory()).append(";")
              .append(product.getPrice()).append(";")
              .append(product.getDescription()).append(";")
              .append(product.getBrand()).append(";")
              .append(product.getRating()).append(";")
              .append(product.getReviewCount()).append(";")
              .append(product.getStock()).append(";")
              .append(product.getDiscountedPrice()).append(";")
              .append(mapToString(product.getSpecifications())).append(";")
              .append(product.getShippingInfo()).append(";")
              .append(product.getWarranty()).append(";")
              .append(String.join(",", product.getTags()));  // Tags as comma-separated
            writer.println(sb.toString());
        }
    } catch (IOException e) {
        System.err.println("Error saving trash list to file: " + e.getMessage());
        e.printStackTrace();
    }
}

    public static void loadTrashFromFile() {
        trashList.clear();
        File file = new File(trashFilePath);
        if (!file.exists()) {
            try {
                file.createNewFile(); 
            } catch (IOException e) {
                return;
            }
    }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 14) {
                    Product product = new Product(
                    parts[0],  // ID
                    parts[1],  // Name
                    parts[2],  // Category
                    Double.parseDouble(parts[3]),  // Price
                    parts[4],  // Description
                    parts[5],  // Brand
                    Double.parseDouble(parts[6]),  // Rating
                    Integer.parseInt(parts[7]),  // Review Count
                    Integer.parseInt(parts[8]),  // Stock
                    Double.parseDouble(parts[9]),  // Discounted Price
                    parseSpecifications(parts[10]),  // Specifications
                    parts[11],  // Shipping Info
                    parts[12],  // Warranty
                    parseTags(parts[13])  
                    ) {};
                 trashList.add(product);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading trash: " + e.getMessage());
        }
}
    public void deleteById(String id) {
        Product toDelete = null;
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(id)) {
                toDelete = p;
                break;
            }
        }
        if (toDelete != null) {
            productList.remove(toDelete);
            trashList.add(toDelete);
            saveToFile();  // Save updated product list
            saveTrashToFile();  // Save updated trash list to a separate file
            System.out.println("Product moved to trash.");
        } else {
            System.out.println("Product not found.");
        }
    }



    public static Map<String, String> parseSpecifications(String specStr) {
        Map<String, String> specs = new HashMap<>();
        if (specStr == null || specStr.trim().isEmpty()) return specs;

        String[] pairs = specStr.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                specs.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return specs;
    }
    public static List<String> parseTags(String tagsStr) {
        List<String> tags = new ArrayList<>();
        if (tagsStr == null || tagsStr.trim().isEmpty()) return tags;

        String[] tagArray = tagsStr.split(",");
        for (String tag : tagArray) {
            tags.add(tag.trim());
        }
        return tags;
    }
   public void viewTrash() {
    if (trashList.isEmpty()) {
        System.out.println("Trash is empty.");
    } else {
        System.out.println("=== Deleted Products ===");
        for (Product p : trashList) {
            System.out.println("ID: " + p.getId() + ", Name: " + p.getName());
        }
    }
} 
    
}