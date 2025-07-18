package All;

import java.util.Scanner;

public class DiscountCheckout implements CheckoutStrategy {

    private double deliveryFee = 5.00;  // Default delivery fee
    private double discountRate = 0;

    @Override
    public double calculateTotal(double cartTotal) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your discount code: ");
            String discountCode = scanner.nextLine().trim();

            if (discountCode.equalsIgnoreCase("DISCOUNT10")) {
                discountRate = 0.10;
                System.out.println("Discount code accepted! 10% discount applied.");
                break;
            } else if (discountCode.equalsIgnoreCase("DISCOUNT50")) {
                discountRate = 0.50;
                System.out.println("Discount code accepted! 50% discount applied.");
                break;
            } else if (discountCode.equalsIgnoreCase("FREESHIP")) {
                deliveryFee = 0;
                System.out.println("Free shipping applied!");
                break;
            } else {
                System.out.println("Invalid discount code.");
                System.out.print("Would you like to try again? (Y/N): ");
                String tryAgain = scanner.nextLine().trim();
                if (!tryAgain.equalsIgnoreCase("Y")) {
                    System.out.println("Proceeding with normal checkout.");
                    break;
                }
            }
        }

        double discountedTotal = cartTotal * (1 - discountRate);
        return discountedTotal + deliveryFee;
    }

    @Override
    public double getDeliveryFee() {
        return deliveryFee;
    }
}
