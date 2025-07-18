package All;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getUnitPrice() {
        return product.getPrice(); // access from Product
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
    
    @Override 
    public String toString() {
    return String.format("%-20s Qty: %d | Unit Price: RM%.2f | Subtotal: RM%.2f",
        product.getName(), quantity, getUnitPrice(), getSubtotal());
    }   //System.out.println(cartItem);

}
