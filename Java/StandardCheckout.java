package All;

public class StandardCheckout implements CheckoutStrategy {
    private final double deliveryFee = 5.00;

    @Override
    public double calculateTotal(double cartTotal) {
        return cartTotal + deliveryFee;
    }

    @Override
    public double getDeliveryFee() {
        return deliveryFee;
    }
}
