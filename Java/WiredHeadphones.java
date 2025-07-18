package All;

import java.util.List;
import java.util.Map;

public class WiredHeadphones extends Product {
    private String cableLength; 

    public WiredHeadphones(String id, String name, String category, double price,
                           String description, String brand, double rating, int reviewCount,
                           int stock, double discountedPrice, Map<String, String> specifications,
                           String shippingInfo, String warranty, List<String> tags) {
       
        super(id, name, category, price, description, brand, rating, reviewCount,
              stock, discountedPrice, specifications, shippingInfo, warranty, tags);

        this.cableLength = specifications.get("Cable Length");
    }

    public String getCableLength() {
        return cableLength;
    }

    @Override
    public String toString() {
        return super.toString() + "\nCable Length: " + cableLength;
    }

    @Override
    public String toCSV() {
        String csv = super.toCSV();
        return csv + ";" + cableLength;
    }
}
