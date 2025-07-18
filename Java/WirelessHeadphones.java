package All;
import java.util.List;
import java.util.Map;

public class WirelessHeadphones extends Product {
    private String batteryLife; 
    
    public WirelessHeadphones(String id, String name, String category, double price, String description, String brand, 
                               double rating, int reviewCount, int stock, double discountedPrice, 
                               Map<String, String> specifications, String shippingInfo, String warranty, 
                               List<String> tags) {
        super(id, name, category, price, description, brand, rating, reviewCount, stock, discountedPrice, 
              specifications, shippingInfo, warranty, tags);
      
        this.batteryLife = specifications.get("Battery");
    }

    public String getBatteryLife() {
        return batteryLife;
    }

    @Override
    public String toString() {
        return super.toString() + " - Battery Life: " + batteryLife;
    }

    @Override
    public String toCSV() {
        // Adding battery life to the CSV representation
        String csv = super.toCSV();
        return csv + "," + batteryLife;
    }
}
