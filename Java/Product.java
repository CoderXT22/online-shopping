package All;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Product{
    private final String id;
    private String name;
    private String category;
    private double price;
    private String description;
    private String brand;
    private double rating;
    private int reviewCount;
    private int stock;
    private double discountedPrice;
    private Map<String, String> specifications;
    private String shippingInfo;
    String warranty;
    private List<String> tags;

    public Product(String id, String name, String category, double price,
                     String description, String brand, double rating, int reviewCount,
                     int stock, double discountedPrice, Map<String, String> specifications,
                     String shippingInfo, String warranty, List<String> tags) {
        this.id = id;
        this.name = name;
        this.category = category;
        setPrice(price);
        this.description = description;
        this.brand = brand;
        setRating(rating);
        this.reviewCount = reviewCount;
        setStock(stock);
        setDiscountedPrice(discountedPrice);
        this.specifications = specifications;
        this.shippingInfo = shippingInfo;
        this.warranty = warranty;
        this.tags = tags;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getBrand() { return brand; }
    public double getRating() { return rating; }
    public int getReviewCount() { return reviewCount; }
    public int getStock() { return stock; }
    public double getDiscountedPrice() { return discountedPrice; }
    public Map<String, String> getSpecifications() { return specifications; }
    public String getShippingInfo() { return shippingInfo; }
    public String getWarranty() { return warranty; }
    public List<String> getTags() { return tags; }
    
    public void setName(String name) { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) {
    if (price >= 0) {
        this.price = price;
    } else {
        throw new IllegalArgumentException("Price cannot be negative.");
    }
}
    public void setDescription(String description) { this.description = description; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setRating(double rating) {
        if (rating >= 0 && rating <= 5) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating must be between 0 and 5.");
        }
    }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }
    public void setStock(int stock) {
    if (stock >= 0) {
        this.stock = stock;
    } else {
        throw new IllegalArgumentException("Stock cannot be negative.");
    }
}
    public void setDiscountedPrice(double discountedPrice) {
        if (discountedPrice >= 0) {
            this.discountedPrice = discountedPrice;
        } else {
            throw new IllegalArgumentException("Discounted price cannot be negative.");
        }
    }
    public void setSpecifications(Map<String, String> specifications) { this.specifications = specifications; }
    public void setShippingInfo(String shippingInfo) { this.shippingInfo = shippingInfo; }
    public void setWarranty(String warranty) { this.warranty = warranty; }
    public void setTags(List<String> tags) { this.tags = tags; }
    
    
    @Override
    public String toString() {
        return "Product ID: " + id + "\n" +
               "Name: " + name + "\n" +
               "Category: " + category + "\n" +
               "Price: " + price + "\n" +
               "Description: " + description + "\n" +
               "Brand: " + brand + "\n" +
               "Rating: " + rating + " (" + reviewCount + " reviews)\n" +
               "Stock: " + stock + "\n" +
               "Discounted Price: " + discountedPrice + "\n" +
               "Shipping Info: " + shippingInfo + "\n" +
               "Warranty: " + warranty + "\n" +
               "Specifications: " + specifications.toString() + "\n" +
               "Tags: " + tags.toString();
    }

    
    public String toCSV() {//Turn into a single text line for saving
        String specs = specifications.entrySet()
                .stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(","));
        String tagString = String.join(",", tags);

        return String.join(";",
                id, name, category, String.valueOf(price), description, brand,
                String.valueOf(rating), String.valueOf(reviewCount), String.valueOf(stock),
                String.valueOf(discountedPrice), specs, shippingInfo, warranty, tagString);
    }
}

