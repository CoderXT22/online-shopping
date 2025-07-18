package All;

public interface CheckoutStrategy {
    double calculateTotal(double cartTotal);
    double getDeliveryFee();
}
